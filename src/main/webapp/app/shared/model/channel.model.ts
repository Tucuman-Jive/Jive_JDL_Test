import { IMember } from 'app/shared/model/member.model';
import { IMessage } from 'app/shared/model/message.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';

export interface IChannel {
  id?: number;
  name?: string | null;
  description?: string | null;
  members?: IMember[] | null;
  message?: IMessage | null;
  userProfiles?: IUserProfile[] | null;
}

export const defaultValue: Readonly<IChannel> = {};
