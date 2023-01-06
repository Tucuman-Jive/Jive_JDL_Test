package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.Member;
import rocks.zipcode.repository.MemberRepository;
import rocks.zipcode.service.dto.MemberDTO;
import rocks.zipcode.service.mapper.MemberMapper;

/**
 * Integration tests for the {@link MemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_CHANNEL_ID = 1L;
    private static final Long UPDATED_CHANNEL_ID = 2L;

    private static final String ENTITY_API_URL = "/api/members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member().userId(DEFAULT_USER_ID).channelID(DEFAULT_CHANNEL_ID);
        return member;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member().userId(UPDATED_USER_ID).channelID(UPDATED_CHANNEL_ID);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMember.getChannelID()).isEqualTo(DEFAULT_CHANNEL_ID);
    }

    @Test
    @Transactional
    void createMemberWithExistingId() throws Exception {
        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelID").value(hasItem(DEFAULT_CHANNEL_ID.intValue())));
    }

    @Test
    @Transactional
    void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.channelID").value(DEFAULT_CHANNEL_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember.userId(UPDATED_USER_ID).channelID(UPDATED_CHANNEL_ID);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMember.getChannelID()).isEqualTo(UPDATED_CHANNEL_ID);
    }

    @Test
    @Transactional
    void putNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember.userId(UPDATED_USER_ID);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMember.getChannelID()).isEqualTo(DEFAULT_CHANNEL_ID);
    }

    @Test
    @Transactional
    void fullUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember.userId(UPDATED_USER_ID).channelID(UPDATED_CHANNEL_ID);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMember.getChannelID()).isEqualTo(UPDATED_CHANNEL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, member.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
