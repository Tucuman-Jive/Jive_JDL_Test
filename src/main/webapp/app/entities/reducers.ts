import userProfile from 'app/entities/user-profile/user-profile.reducer';
import member from 'app/entities/member/member.reducer';
import directMessage from 'app/entities/direct-message/direct-message.reducer';
import channel from 'app/entities/channel/channel.reducer';
import message from 'app/entities/message/message.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  userProfile,
  member,
  directMessage,
  channel,
  message,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
