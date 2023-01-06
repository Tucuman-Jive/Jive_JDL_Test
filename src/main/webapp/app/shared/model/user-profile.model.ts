import { IUser } from 'app/shared/model/user.model';
import { IMessage } from 'app/shared/model/message.model';
import { IChannel } from 'app/shared/model/channel.model';
import { IDirectMessage } from 'app/shared/model/direct-message.model';

export interface IUserProfile {
  id?: number;
  displayName?: string | null;
  profileImageContentType?: string | null;
  profileImage?: string | null;
  user?: IUser | null;
  messages?: IMessage[] | null;
  channels?: IChannel[] | null;
  directMessages?: IDirectMessage[] | null;
}

export const defaultValue: Readonly<IUserProfile> = {};
