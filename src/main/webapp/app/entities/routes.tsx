import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserProfile from './user-profile';
import Member from './member';
import DirectMessage from './direct-message';
import Channel from './channel';
import Message from './message';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="user-profile/*" element={<UserProfile />} />
        <Route path="member/*" element={<Member />} />
        <Route path="direct-message/*" element={<DirectMessage />} />
        <Route path="channel/*" element={<Channel />} />
        <Route path="message/*" element={<Message />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
