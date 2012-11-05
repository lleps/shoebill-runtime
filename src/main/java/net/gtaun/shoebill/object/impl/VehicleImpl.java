/**
 * Copyright (C) 2011-2012 MK124
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

package net.gtaun.shoebill.object.impl;

import net.gtaun.shoebill.SampObjectPoolImpl;
import net.gtaun.shoebill.ShoebillImpl;
import net.gtaun.shoebill.ShoebillLowLevel;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.data.LocationAngle;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.data.Quaternion;
import net.gtaun.shoebill.data.Velocity;
import net.gtaun.shoebill.events.vehicle.VehicleDestroyEvent;
import net.gtaun.shoebill.events.vehicle.VehicleSpawnEvent;
import net.gtaun.shoebill.exception.CreationFailedException;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Vehicle;
import net.gtaun.shoebill.object.VehicleComponent;
import net.gtaun.shoebill.object.VehicleDamage;
import net.gtaun.shoebill.object.VehicleParam;
import net.gtaun.shoebill.proxy.ProxyManager;
import net.gtaun.shoebill.samp.SampNativeFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author MK124, JoJLlmAn
 */
public class VehicleImpl implements Vehicle
{
	public static void manualEngineAndLights()
	{
		SampNativeFunction.manualVehicleEngineAndLights();
	}
	

	private ProxyManager proxyManager;
	
	private boolean isStatic = false;
	
	private int id = INVALID_ID;
	private int modelId;
	private int interiorId;
	private int color1, color2;
	private int respawnDelay;

	private VehicleParamImpl param;
	private VehicleComponentImpl component;
	private VehicleDamageImpl damage;
	
	
	public VehicleImpl( int modelId, LocationAngle loc, int color1, int color2, int respawnDelay ) throws CreationFailedException
	{
		initialize( modelId, loc.getX(), loc.getY(), loc.getZ(), loc.getInteriorId(), loc.getWorldId(), loc.getAngle(), color1, color2, respawnDelay );
	}
	
	private void initialize( int modelId, float x, float y, float z, int interiorId, int worldId, float angle, int color1, int color2, int respawnDelay ) throws CreationFailedException
	{
		this.modelId = modelId;
		this.interiorId = interiorId;
		this.color1 = color1;
		this.color2 = color2;
		this.respawnDelay = respawnDelay;
		
		switch( modelId )
		{
			case 537:
			case 538:
			case 569:
			case 570:
			case 590:
				id = SampNativeFunction.addStaticVehicle( modelId, x, y, z, angle, color1, color2 );
				isStatic = true;
				break;
							
			default:
				id = SampNativeFunction.createVehicle( modelId, x, y, z, angle, color1, color2, respawnDelay );
		}
		
		if( id == INVALID_ID ) throw new CreationFailedException();
		
		SampNativeFunction.linkVehicleToInterior( id, interiorId );
		SampNativeFunction.setVehicleVirtualWorld( id, worldId );

		param = new VehicleParamImpl(this);
		component = new VehicleComponentImpl(this);
		damage = new VehicleDamageImpl(this);
		
		VehicleSpawnEvent event = new VehicleSpawnEvent( this );
		ShoebillLowLevel shoebillLowLevel = (ShoebillLowLevel) ShoebillImpl.getInstance();
		shoebillLowLevel.getEventManager().dispatchEvent( event, this );
	}

	public void processVehicleMod()
	{
		component.update();
	}
	
	public void processVehicleDamageStatusUpdate()
	{
		SampNativeFunction.getVehicleDamageStatus( id, damage );
	}

	@Override
	public ProxyManager getProxyManager()
	{
		return proxyManager;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	@Override
	public void destroy()
	{
		if( isDestroyed() ) return;
		if( isStatic ) return;
		
		VehicleDestroyEvent event = new VehicleDestroyEvent( this );
		ShoebillLowLevel shoebillLowLevel = (ShoebillLowLevel) ShoebillImpl.getInstance();
		shoebillLowLevel.getEventManager().dispatchEvent( event, this );
		
		SampNativeFunction.destroyVehicle( id );

		SampObjectPoolImpl pool = (SampObjectPoolImpl) ShoebillImpl.getInstance().getSampObjectPool();
		pool.setVehicle( id, null );
		
		id = INVALID_ID;
	}
	
	@Override
	public boolean isDestroyed()
	{
		return id == INVALID_ID;
	}
	
	@Override
	public int getId()
	{
		return id;
	}
	
	@Override
	public boolean isStatic()
	{
		return isStatic;
	}
	
	@Override
	public int getModelId()
	{
		return modelId;
	}
	
	@Override
	public int getColor1()
	{
		return color1;
	}
	
	@Override
	public int getColor2()
	{
		return color2;
	}
	
	@Override
	public int getRespawnDelay()
	{
		return respawnDelay;
	}
	
	@Override
	public VehicleParam getState()
	{
		return param;
	}
	
	@Override
	public VehicleComponent getComponent()
	{
		return component;
	}
	
	@Override
	public VehicleDamage getDamage()
	{
		return damage;
	}
	
	@Override
	public LocationAngle getLocation()
	{
		if( isDestroyed() ) return null;
		
		LocationAngle location = new LocationAngle();

		SampNativeFunction.getVehiclePos( id, location );
		location.setAngle( SampNativeFunction.getVehicleZAngle(id) );
		location.setInteriorId( interiorId );
		location.setWorldId( SampNativeFunction.getVehicleVirtualWorld(id) );
		
		return location;
	}

	@Override
	public void setLocation( float x, float y, float z )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehiclePos( id, x, y, z );
	}

	@Override
	public void setLocation( Vector3D pos )
	{
		setLocation( pos.getX(), pos.getY(), pos.getZ() );
	}
	
	@Override
	public void setLocation( Location loc )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehiclePos( id, loc.getX(), loc.getY(), loc.getZ() );
		SampNativeFunction.linkVehicleToInterior( id, loc.getInteriorId() );
		SampNativeFunction.setVehicleVirtualWorld( id, loc.getWorldId() );
	}
	
	@Override
	public void setLocation( LocationAngle loc )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehiclePos( id, loc.getX(), loc.getY(), loc.getZ() );
		SampNativeFunction.setVehicleZAngle( id, loc.getAngle() );
		SampNativeFunction.linkVehicleToInterior( id, loc.getInteriorId() );
		SampNativeFunction.setVehicleVirtualWorld( id, loc.getWorldId() );
	}
	
	@Override
	public float getAngle()
	{
		if( isDestroyed() ) return 0.0F;
		
		return SampNativeFunction.getVehicleZAngle(id);
	}
	
	@Override
	public void setAngle( float angle )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleZAngle( id, angle );
	}

	@Override
	public Quaternion getRotationQuat()
	{
		if( isDestroyed() ) return null;
		
		Quaternion quaternions = new Quaternion();
		SampNativeFunction.getVehicleRotationQuat( id, quaternions );
		
		return quaternions;
	}
	
	@Override
	public int getInteriorId()
	{
		return interiorId;
	}
	
	@Override
	public void setInteriorId( int interiorId )
	{
		if( isDestroyed() ) return;
		
		this.interiorId = interiorId;
		SampNativeFunction.linkVehicleToInterior( id, interiorId );
	}
	
	@Override
	public int getWorldId()
	{
		if( isDestroyed() ) return 0;
		
		return SampNativeFunction.getVehicleVirtualWorld(id);
	}
	
	@Override
	public void setWorldId( int worldId )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleVirtualWorld( id, worldId );
	}
	
	@Override
	public float getHealth()
	{
		if( isDestroyed() ) return 0.0F;
		
		return SampNativeFunction.getVehicleHealth(id);
	}
	
	@Override
	public void setHealth( float health )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleHealth( id, health );
	}

	@Override
	public Velocity getVelocity()
	{
		if( isDestroyed() ) return null;
		
		Velocity velocity = new Velocity();
		SampNativeFunction.getVehicleVelocity( id, velocity );
		
		return velocity;
	}
	
	@Override
	public void setVelocity( Velocity velocity )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleVelocity( id, velocity.getX(), velocity.getY(), velocity.getZ() );
	}

	@Override
	public void putPlayer( Player player, int seat )
	{
		if( isDestroyed() ) return;
		if( player.isOnline() == false ) return;
		
		SampNativeFunction.putPlayerInVehicle( player.getId(), id, seat );
	}
	
	@Override
	public boolean isPlayerIn( Player player )
	{
		if( isDestroyed() ) return false;
		if( player.isOnline() == false ) return false;
		
		return SampNativeFunction.isPlayerInVehicle(player.getId(), id);
	}
	
	@Override
	public boolean isStreamedIn( Player forPlayer )
	{
		if( isDestroyed() ) return false;
		if( forPlayer.isOnline() == false ) return false;
		
		return SampNativeFunction.isVehicleStreamedIn(id, forPlayer.getId());
	}
	
	@Override
	public void setParamsForPlayer( Player player, boolean objective, boolean doorslocked )
	{
		if( isDestroyed() ) return;
		if( player.isOnline() == false ) return;
		
		SampNativeFunction.setVehicleParamsForPlayer( id, player.getId(), objective, doorslocked );
	}
	
	@Override
	public void respawn()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleToRespawn( id );
	}
	
	@Override
	public void setColor( int color1, int color2 )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.changeVehicleColor( id, color1, color2 );
	}
	
	@Override
	public void setPaintjob( int paintjobId )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.changeVehiclePaintjob( id, paintjobId );
	}

	@Override
	public Vehicle getTrailer()
	{
		if( isDestroyed() ) return null;
		
		int trailerId = SampNativeFunction.getVehicleTrailer(id);
		return ShoebillImpl.getInstance().getSampObjectPool().getVehicle(trailerId);
	}
	
	@Override
	public void attachTrailer( Vehicle trailer )
	{
		if( isDestroyed() ) return;
		if( trailer.isDestroyed() ) return;
		
		SampNativeFunction.attachTrailerToVehicle( trailer.getId(), id );
	}
	
	@Override
	public void detachTrailer()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.detachTrailerFromVehicle( id );
	}
	
	@Override
	public boolean isTrailerAttached()
	{
		if( isDestroyed() ) return false;
		
		return SampNativeFunction.isTrailerAttachedToVehicle(id);
	}
	
	@Override
	public void setNumberPlate( String numberplate )
	{
		if( isDestroyed() ) return;
		
		if( numberplate == null ) throw new NullPointerException();
		SampNativeFunction.setVehicleNumberPlate( id, numberplate );
	}
	
	@Override
	public void repair()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.repairVehicle( id );
	}
	
	@Override
	public void setAngularVelocity( Velocity velocity )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.setVehicleAngularVelocity( id, velocity.getX(), velocity.getY(), velocity.getZ() );
	}
}
