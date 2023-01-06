import { IMessage } from 'app/shared/model/message.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';

export interface IDirectMessage {
  id?: number;
  userId1?: number | null;
  userId2?: number | null;
  messages?: IMessage[] | null;
  userProfiles?: IUserProfile[] | null;
}

export const defaultValue: Readonly<IDirectMessage> = {};
