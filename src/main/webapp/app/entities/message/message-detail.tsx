import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './message.reducer';

export const MessageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const messageEntity = useAppSelector(state => state.message.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="messageDetailsHeading">
          <Translate contentKey="jiveApp.message.detail.title">Message</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{messageEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="jiveApp.message.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{messageEntity.userId}</dd>
          <dt>
            <span id="channelID">
              <Translate contentKey="jiveApp.message.channelID">Channel ID</Translate>
            </span>
          </dt>
          <dd>{messageEntity.channelID}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="jiveApp.message.text">Text</Translate>
            </span>
          </dt>
          <dd>{messageEntity.text}</dd>
          <dt>
            <Translate contentKey="jiveApp.message.userProfile">User Profile</Translate>
          </dt>
          <dd>{messageEntity.userProfile ? messageEntity.userProfile.id : ''}</dd>
          <dt>
            <Translate contentKey="jiveApp.message.directMessage">Direct Message</Translate>
          </dt>
          <dd>{messageEntity.directMessage ? messageEntity.directMessage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/message/${messageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MessageDetail;
