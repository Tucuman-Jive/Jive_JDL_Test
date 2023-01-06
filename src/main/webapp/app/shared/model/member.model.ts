import { IChannel } from 'app/shared/model/channel.model';

export interface IMember {
  id?: number;
  userId?: number | null;
  channelID?: number | null;
  channels?: IChannel[] | null;
}

export const defaultValue: Readonly<IMember> = {};
