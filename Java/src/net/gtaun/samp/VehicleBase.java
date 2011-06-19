/**
 * Copyright (C) 2011 MK124
 * Copyright (C) 2011 JoJLlmAn
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

package net.gtaun.samp;

import java.util.Vector;

import net.gtaun.event.EventDispatcher;
import net.gtaun.event.IEventDispatcher;
import net.gtaun.samp.data.Point;
import net.gtaun.samp.data.PointAngle;
import net.gtaun.samp.data.Speed;
import net.gtaun.samp.data.VehicleDamage;
import net.gtaun.samp.data.VehicleState;
import net.gtaun.samp.event.VehicleDeathEvent;
import net.gtaun.samp.event.VehicleDestroyEvent;
import net.gtaun.samp.event.VehicleEnterEvent;
import net.gtaun.samp.event.VehicleExitEvent;
import net.gtaun.samp.event.VehicleModEvent;
import net.gtaun.samp.event.VehiclePaintjobEvent;
import net.gtaun.samp.event.VehicleResprayEvent;
import net.gtaun.samp.event.VehicleSpawnEvent;
import net.gtaun.samp.event.VehicleStreamInEvent;
import net.gtaun.samp.event.VehicleStreamOutEvent;
import net.gtaun.samp.event.VehicleUpdateDamageEvent;
import net.gtaun.samp.event.VehicleUpdateEvent;

/**
 * @author MK124, JoJLlmAn
 *
 */

public class VehicleBase
{
	public static final int PARAMS_UNSET =			-1;
	public static final int PARAMS_OFF =			0;
	public static final int PARAMS_ON =				1;
	
//---------------------------------------------------------
	
	public static <T> Vector<T> get( Class<T> cls )
	{
		return GameModeBase.getInstances(GameModeBase.instance.vehiclePool, cls);
	}
	
	public static <T> T get( Class<T> cls, int id )
	{
		return GameModeBase.getInstance(GameModeBase.instance.vehiclePool, cls, id);
	}
	
	public static void manualEngineAndLights()
	{
		NativeFunction.manualVehicleEngineAndLights();
	}
	
	
	boolean isStatic = false, isDestroyed = false;
	
	int id;
	int model;
	PointAngle position = new PointAngle();
	int color1, color2;
	int respawnDelay;
	int interior;

	VehicleState state = new VehicleState();
	VehicleComponent components;

	public boolean isStatic()					{ return isStatic; }
	public boolean isDestroyed()				{ return isDestroyed; }
	
	public int model()							{ return model; }
	public PointAngle position()				{ return position.clone(); }
	public int color1()							{ return color1; }
	public int color2()							{ return color2; }
	public int respawnDelay()					{ return respawnDelay; }

	public VehicleState state()					{ return state.clone(); }
	public VehicleComponent components()		{ return components; }
	
	
	EventDispatcher<VehicleDestroyEvent>		eventDestroy;
	EventDispatcher<VehicleSpawnEvent>			eventSpawn;
	EventDispatcher<VehicleDeathEvent>			eventDeath;
	EventDispatcher<VehicleUpdateEvent>			eventUpdate;
	EventDispatcher<VehicleEnterEvent>			eventEnter;
	EventDispatcher<VehicleExitEvent>			eventExit;
	EventDispatcher<VehicleModEvent>			eventMod;
	EventDispatcher<VehiclePaintjobEvent>		eventPaintjob;
	EventDispatcher<VehicleResprayEvent>		eventRespray;
	EventDispatcher<VehicleUpdateDamageEvent>	eventUpdateDamage;
	EventDispatcher<VehicleStreamInEvent>		eventStreamIn;
	EventDispatcher<VehicleStreamOutEvent>		eventStreamOut;

	public IEventDispatcher<VehicleDestroyEvent>		eventDestroy()			{ return eventDestroy; }
	public IEventDispatcher<VehicleSpawnEvent>			eventSpawn()			{ return eventSpawn; }
	public IEventDispatcher<VehicleDeathEvent>			eventDeath()			{ return eventDeath; }
	public IEventDispatcher<VehicleUpdateEvent>			eventUpdate()			{ return eventUpdate; }
	public IEventDispatcher<VehicleEnterEvent>			eventEnter()			{ return eventEnter; }
	public IEventDispatcher<VehicleExitEvent>			eventExit()				{ return eventExit; }
	public IEventDispatcher<VehicleModEvent>			eventMod()				{ return eventMod; }
	public IEventDispatcher<VehiclePaintjobEvent>		eventPaintjob()			{ return eventPaintjob; }
	public IEventDispatcher<VehicleResprayEvent>		eventRespray()			{ return eventRespray; }
	public IEventDispatcher<VehicleUpdateDamageEvent>	eventUpdateDamage()		{ return eventUpdateDamage; }
	public IEventDispatcher<VehicleStreamInEvent>		eventStreamIn()			{ return eventStreamIn; }
	public IEventDispatcher<VehicleStreamOutEvent>		eventStreamOut()		{ return eventStreamOut; }
	
	
	public VehicleBase( int model, float x, float y, float z, float angle, int color1, int color2, int respawnDelay )
	{
		this.model = model;
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		this.position.angle = angle;
		this.color1 = color1;
		this.color2 = color2;
		this.respawnDelay = respawnDelay;
		
		init();
	}
	
	public VehicleBase( int model, Point point, float angle, int color1, int color2, int respawnDelay )
	{
		this.model = model;
		this.position = new PointAngle( point, angle );
		this.color1 = color1;
		this.color2 = color2;
		this.respawnDelay = respawnDelay;
		
		init();
	}
	
	public VehicleBase( int model, PointAngle point, int color1, int color2, int respawnDelay )
	{
		this.model = model;
		this.position = point.clone();
		this.color1 = color1;
		this.color2 = color2;
		this.respawnDelay = respawnDelay;
		
		init();
	}
	
	private void init()
	{
		id = NativeFunction.createVehicle( model, position.x, position.y, position.z, position.angle, color1, color2, respawnDelay );
		NativeFunction.linkVehicleToInterior( id, position.interior );
		interior = position.interior;
		
		NativeFunction.getVehicleParamsEx(id, state);
		components = new VehicleComponent(id);
	}
	
	
//---------------------------------------------------------
	
	protected int onDestroy()
	{
		return 1;
	}
	
	protected int onSpawn()
	{
		return 1;
	}

	protected int onDeath( PlayerBase killer )
	{
		return 1;
	}
	
	protected int onUpdate()
	{
		NativeFunction.getVehicleParamsEx(id, state);
		return 1;
	}
	
	protected int onEnter( PlayerBase player, boolean ispassenger )
	{
		return 1;
	}

	protected int onExit( PlayerBase player )
	{
		return 1;
	}
	
	protected int onMod( int componentid )
	{
		int type = NativeFunction.getVehicleComponentType(componentid);
		this.components.componentData[type] = NativeFunction.getVehicleComponentInSlot(id, type);
		
		return 1;
	}

	protected int onPaintjob( int paintjobid )
	{
		return 1;
	}

	protected int onRespray( int color1, int color2 )
	{
		return 1;
	}

	protected int onUpdateDamage( PlayerBase player )
	{
		return 1;
	}

	protected int onStreamIn( PlayerBase player )
	{
		return 1;
	}

	protected int onStreamOut( PlayerBase player )
	{
		return 1;
	}
	
	
//---------------------------------------------------------
		
	void update()
	{
		
	}
	
	
//---------------------------------------------------------
	
	public void destroy()
	{
		if( isStatic ) return;
		
		NativeFunction.destroyVehicle( id );
		
		isDestroyed = true;
		GameModeBase.instance.vehiclePool[ id ] = null;
		
		onDestroy();
		eventDestroy.dispatchEvent( new VehicleDestroyEvent(this) );
	}
	
	public VehicleState getParamsEx()
	{
		VehicleState state = new VehicleState();
		NativeFunction.getVehicleParamsEx( id, state );
		
		return state;
	}
	
	public VehicleBase getTrailer()
	{
		return get( VehicleBase.class, NativeFunction.getVehicleTrailer(id) );
	}
	
	public VehicleDamage getDamageStatus()
	{
		VehicleDamage damage = new VehicleDamage();
		NativeFunction.getVehicleDamageStatus( id, damage );
		
		return damage;
	}
	
	public void putPlayer( PlayerBase player, int seat )
	{
		NativeFunction.putPlayerInVehicle( player.id, id, seat );
	}
	
	public boolean isPlayerIn( PlayerBase player )
	{
		return NativeFunction.isPlayerInVehicle(player.id, id);
	}
	
	public boolean isStreamedIn( PlayerBase forplayer )
	{
		return NativeFunction.isVehicleStreamedIn(id, forplayer.id);
	}
	
	public void setParamsForPlayer( PlayerBase player, boolean objective, boolean doorslocked )
	{
		NativeFunction.setVehicleParamsForPlayer( id, player.id, objective, doorslocked );
	}
	
	public void setParamsEx( boolean engine, boolean lights, boolean alarm, boolean doors, boolean bonnet, boolean boot, boolean objective )
	{
		NativeFunction.setVehicleParamsEx( id, engine, lights, alarm, doors, bonnet, boot, objective );
	}
	
	public void setParamsEx( VehicleState state )
	{
		NativeFunction.setVehicleParamsEx( id, state.engine, state.lights, state.alarm, state.doors,
				state.bonnet, state.boot, state.objective );
	}
	
	public void respawn()
	{
		NativeFunction.setVehicleToRespawn( id );
	}
	
	public void setColor( int color1, int color2 )
	{
		NativeFunction.changeVehicleColor( id, color1, color2 );
	}
	
	public void setPaintjob( int paintjobid )
	{
		NativeFunction.changeVehiclePaintjob( id, paintjobid );
	}
	
	public void attachTrailer( VehicleBase trailer )
	{
		NativeFunction.attachTrailerToVehicle( trailer.id, id );
	}
	
	public void detachTrailer()
	{
		NativeFunction.detachTrailerFromVehicle( id );
	}
	
	public boolean isTrailerAttached()
	{
		return NativeFunction.isTrailerAttachedToVehicle(id);
	}
	
	public void setNumberPlate( String numberplate )
	{
		NativeFunction.setVehicleNumberPlate( id, numberplate );
	}
	
	public void repair()
	{
		NativeFunction.repairVehicle( id );
	}
	
	public void setAngularVelocity( float x, float y, float z )
	{
		NativeFunction.setVehicleAngularVelocity( id, x, y, z );
	}

	public void setDamageStatus( int panels, int doors, int lights,int tires )
	{
		NativeFunction.updateVehicleDamageStatus( id, panels, doors, lights, tires );
	}

	public void setDamageStatus( VehicleDamage damage )
	{
		NativeFunction.updateVehicleDamageStatus( id, damage.panels, damage.doors, damage.lights, damage.tires );
	}
	
	public Point getPos()
	{
		Point point = new Point();
		NativeFunction.getVehiclePos(id, point);
		return point;
	}
	
	public PointAngle getPosAngle()
	{
		PointAngle point = new PointAngle();
		NativeFunction.getVehiclePos(id, point);
		point.angle = NativeFunction.getVehicleZAngle(id);
		return point;
	}
	
	public void setPos( Point point )
	{
		NativeFunction.setVehiclePos(id, point.x, point.y, point.z);
		NativeFunction.linkVehicleToInterior(id, point.interior);
		this.interior = point.interior;
		NativeFunction.setVehicleVirtualWorld(id, point.world);
	}
	
	public void setPos( PointAngle point )
	{
		NativeFunction.setVehiclePos(id, point.x, point.y, point.z);
		NativeFunction.setVehicleZAngle(id, point.angle);
		NativeFunction.linkVehicleToInterior(id, point.interior);
		this.interior = point.interior;
		NativeFunction.setVehicleVirtualWorld(id, point.world);
	}
	
	public void setPos( float x, float y, float z )
	{
		NativeFunction.setVehiclePos(id, x, y, z);
	}
	
	public float getAngle()
	{
		return NativeFunction.getVehicleZAngle(id);
	}
	
	public void setAngle( float angle )
	{
		NativeFunction.setVehicleZAngle(id, angle);
	}
	
	public void setInterior( int interior )
	{
		NativeFunction.linkVehicleToInterior(id, interior);
		this.interior = interior;
	}
	
	public int getInterior()
	{
		return interior;
	}
	
	public void setHealth( float health )
	{
		NativeFunction.setVehicleHealth(id, health);
	}
	
	public float getHealth()
	{
		return NativeFunction.getVehicleHealth(id);
	}
	
	public void setVelocity(Speed speed)
	{
		NativeFunction.setVehicleVelocity(id, speed.x, speed.y, speed.z);
	}
	
	public Speed getVelocity()
	{
		Speed speed = new Speed();
		NativeFunction.getVehicleVelocity(id, speed);
		return speed;
	}
	
	
	
	
	//public static int getComponentType( int component );
}
