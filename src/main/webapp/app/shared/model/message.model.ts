import { IChannel } from 'app/shared/model/channel.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IDirectMessage } from 'app/shared/model/direct-message.model';

export interface IMessage {
  id?: number;
  userId?: number | null;
  channelID?: number | null;
  text?: string | null;
  channels?: IChannel[] | null;
  userProfile?: IUserProfile | null;
  directMessage?: IDirectMessage | null;
}

export const defaultValue: Readonly<IMessage> = {};
