/**
 * Copyright (C) 2011-2014 MK124
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gtaun.shoebill.samp;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * 
 * @author MK124
 */
public class SampCallbackManagerImpl implements SampCallbackManager
{
	private Queue<SampCallbackHandler> callbackHandlers;
	
	
	public SampCallbackManagerImpl()
	{
		callbackHandlers = new ConcurrentLinkedQueue<>();
	}
	
	@Override
	public void registerCallbackHandler(SampCallbackHandler handler)
	{
		callbackHandlers.add(handler);
	}
	
	@Override
	public void unregisterCallbackHandler(SampCallbackHandler handler)
	{
		callbackHandlers.remove(handler);
	}
	
	@Override
	public boolean hasCallbackHandler(SampCallbackHandler handler)
	{
		return callbackHandlers.contains(handler);
	}
	
	@Override
	public SampCallbackHandler getMasterCallbackHandler()
	{
		return callbackHandler;
	}
	
	private SampCallbackHandler callbackHandler = new SampCallbackHandler()
	{
		@Override
		public void onProcessTick()
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onProcessTick();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void onAmxLoad(int handle)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onAmxLoad(handle);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public void onAmxUnload(int handle)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onAmxUnload(handle);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public int onGameModeInit()
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onGameModeInit();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onGameModeExit()
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onGameModeExit();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerConnect(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerConnect(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerDisconnect(int playerid, int reason)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerDisconnect(playerid, reason);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerSpawn(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerSpawn(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerDeath(int playerid, int killerid, int reason)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerDeath(playerid, killerid, reason);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleSpawn(int vehicleid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleSpawn(vehicleid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleDeath(int vehicleid, int killerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleDeath(vehicleid, killerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerText(int playerid, String text)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerText(playerid, text);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerCommandText(int playerid, String cmdtext)
		{
			int ret = 0;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret |= handler.onPlayerCommandText(playerid, cmdtext);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerRequestClass(int playerid, int classid)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerRequestClass(playerid, classid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerEnterVehicle(int playerid, int vehicleid, int ispassenger)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerEnterVehicle(playerid, vehicleid, ispassenger);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerExitVehicle(int playerid, int vehicleid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerExitVehicle(playerid, vehicleid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerStateChange(int playerid, int newstate, int oldstate)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerStateChange(playerid, newstate, oldstate);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerEnterCheckpoint(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerEnterCheckpoint(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerLeaveCheckpoint(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerLeaveCheckpoint(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerEnterRaceCheckpoint(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerEnterRaceCheckpoint(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerLeaveRaceCheckpoint(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerLeaveRaceCheckpoint(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onRconCommand(String cmd)
		{
			int ret = 0;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret |= handler.onRconCommand(cmd);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerRequestSpawn(int playerid)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerRequestSpawn(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onObjectMoved(int objectid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onObjectMoved(objectid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerObjectMoved(int playerid, int objectid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerObjectMoved(playerid, objectid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerPickUpPickup(int playerid, int pickupid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerPickUpPickup(playerid, pickupid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleMod(int playerid, int vehicleid, int componentid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleMod(playerid, vehicleid, componentid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onEnterExitModShop(int playerid, int enterexit, int interiorid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onEnterExitModShop(playerid, enterexit, interiorid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehiclePaintjob(int playerid, int vehicleid, int paintjobid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehiclePaintjob(playerid, vehicleid, paintjobid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleRespray(int playerid, int vehicleid, int color1, int color2)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleRespray(playerid, vehicleid, color1, color2);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleDamageStatusUpdate(int vehicleid, int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleDamageStatusUpdate(vehicleid, playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onUnoccupiedVehicleUpdate(int vehicleid, int playerid, int passenger_seat, float newX, float newY, float newZ)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onUnoccupiedVehicleUpdate(vehicleid, playerid, passenger_seat, newX, newY, newZ);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerSelectedMenuRow(int playerid, int row)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerSelectedMenuRow(playerid, row);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerExitedMenu(int playerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerExitedMenu(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerInteriorChange(int playerid, int newinteriorid, int oldinteriorid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerInteriorChange(playerid, newinteriorid, oldinteriorid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerKeyStateChange(int playerid, int newkeys, int oldkeys)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerKeyStateChange(playerid, newkeys, oldkeys);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onRconLoginAttempt(String ip, String password, int success)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onRconLoginAttempt(ip, password, success);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerUpdate(int playerid)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerUpdate(playerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerStreamIn(int playerid, int forplayerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerStreamIn(playerid, forplayerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerStreamOut(int playerid, int forplayerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerStreamOut(playerid, forplayerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleStreamIn(int vehicleid, int forplayerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleStreamIn(vehicleid, forplayerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onVehicleStreamOut(int vehicleid, int forplayerid)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onVehicleStreamOut(vehicleid, forplayerid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onDialogResponse(int playerid, int dialogid, int response, int listitem, String inputtext)
		{
			int ret = 0;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret |= handler.onDialogResponse(playerid, dialogid, response, listitem, inputtext);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerTakeDamage(int playerId, int issuerId, float amount, int weaponId, int bodypart)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerTakeDamage(playerId, issuerId, amount, weaponId, bodypart);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerGiveDamage(int playerId, int damagedId, float amount, int weaponId, int bodypart)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerGiveDamage(playerId, damagedId, amount, weaponId, bodypart);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerClickMap(int playerId, float x, float y, float z)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerClickMap(playerId, x, y, z);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerClickTextDraw(int playerid, int clickedid)
		{
			int ret = 0;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret |= handler.onPlayerClickPlayerTextDraw(playerid, clickedid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerClickPlayerTextDraw(int playerid, int playertextid)
		{
			int ret = 0;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret |= handler.onPlayerClickPlayerTextDraw(playerid, playertextid);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return ret;
		}
		
		@Override
		public int onPlayerClickPlayer(int playerid, int clickedplayerid, int source)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerClickPlayer(playerid, clickedplayerid, source);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerEditObject(int playerid, int playerobject, int objectid, int response, float fX, float fY, float fZ, float fRotX, float fRotY, float fRotZ)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerEditObject(playerid, playerobject, objectid, response, fX, fY, fZ, fRotX, fRotY, fRotZ);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerEditAttachedObject(int playerid, int response, int index, int modelid, int boneid, float fOffsetX, float fOffsetY, float fOffsetZ, float fRotX, float fRotY, float fRotZ, float fScaleX, float fScaleY, float fScaleZ)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerEditAttachedObject(playerid, response, index, modelid, boneid, fOffsetX, fOffsetY, fOffsetZ, fRotX, fRotY, fRotZ, fScaleX, fScaleY, fScaleZ);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}
		
		@Override
		public int onPlayerSelectObject(int playerid, int type, int objectid, int modelid, float fX, float fY, float fZ)
		{
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					handler.onPlayerSelectObject(playerid, type, objectid, modelid, fX, fY, fZ);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
			
			return 1;
		}

		@Override
		public int onPlayerWeaponShot(int playerid, int weaponid, int hittype, int hitid, float fX, float fY, float fZ)
		{
			int ret = 1;
			for (SampCallbackHandler handler : callbackHandlers)
			{
				try
				{
					ret &= handler.onPlayerWeaponShot(playerid, weaponid, hittype, hitid, fX, fY, fZ);
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}

			return ret;
		}
	};
}
