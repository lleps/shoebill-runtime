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

package net.gtaun.shoebill.object;

import net.gtaun.shoebill.SampObjectPool;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.data.Vector2D;
import net.gtaun.shoebill.exception.CreationFailedException;
import net.gtaun.shoebill.samp.SampNativeFunction;

import org.apache.commons.lang3.StringUtils;


/**
 * @author MK124, JoJLlmAn
 *
 */

public class Textdraw implements ITextdraw
{
	private int id = INVALID_ID;
	private Vector2D position;
	private String text;
	
	private boolean[] isPlayerShowed = new boolean[SampObjectPool.MAX_PLAYERS];


	public Textdraw( float x, float y ) throws CreationFailedException
	{
		initialize( x, y, null );
	}
	
	public Textdraw( float x, float y, String text ) throws CreationFailedException
	{
		initialize( x, y, text );
	}

	public Textdraw( Vector2D pos ) throws CreationFailedException
	{
		initialize( pos.getX(), pos.getY(), null );
	}
	
	public Textdraw( Vector2D pos, String text ) throws CreationFailedException
	{
		initialize( pos.getX(), pos.getY(), text );
	}
	
	private void initialize( float x, float y, String text ) throws CreationFailedException
	{
		position = new Vector2D( x, y );
		if( StringUtils.isEmpty(text) ) text = " ";
		
		id = SampNativeFunction.textDrawCreate( x, y, text );
		if( id == INVALID_ID ) throw new CreationFailedException();
		
		for( int i=0; i<isPlayerShowed.length; i++ ) isPlayerShowed[i] = false;
		
		SampObjectPool pool = (SampObjectPool) Shoebill.getInstance().getManagedObjectPool();
		pool.setTextdraw( id, this );
	}
	

	@Override
	public void destroy()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawDestroy( id );

		SampObjectPool pool = (SampObjectPool) Shoebill.getInstance().getManagedObjectPool();
		pool.setTextdraw( id, null );
		
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
	public Vector2D getPosition()
	{
		return position.clone();
	}
	
	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public void setLetterSize( float x, float y )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawLetterSize( id, x, y );
	}

	@Override
	public void setLetterSize( Vector2D vec )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawLetterSize( id, vec.getX(), vec.getY() );
	}

	@Override
	public void setTextSize( float x, float y )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawTextSize( id, x, y );
	}

	@Override
	public void setTextSize( Vector2D vec )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawTextSize( id, vec.getX(), vec.getY() );
	}

	@Override
	public void setAlignment( int alignment )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawAlignment( id, alignment );
	}

	@Override
	public void setColor( Color color )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawColor( id, color.getValue() );
	}

	@Override
	public void setUseBox( boolean use )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawUseBox( id, use );
	}

	@Override
	public void setBoxColor( Color color )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawBoxColor( id, color.getValue() );
	}

	@Override
	public void setShadow( int size )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawSetShadow( id, size );
	}

	@Override
	public void setOutline( int size )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawSetOutline( id, size );
	}

	@Override
	public void setBackgroundColor( Color color )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawBackgroundColor( id, color.getValue() );
	}

	@Override
	public void setFont( int font )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawFont( id, font );
	}

	@Override
	public void setProportional( int set )
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawSetProportional( id, set );
	}

	@Override
	public void setText( String text )
	{
		if( isDestroyed() ) return;
		
		if( text == null ) throw new NullPointerException();
		
		this.text = text;
		SampNativeFunction.textDrawSetString( id, text );
	}

	@Override
	public void show( IPlayer player )
	{
		if( isDestroyed() ) return;
		if( player.isOnline() == false ) return;
		
		int playerId = player.getId();
		
		SampNativeFunction.textDrawShowForPlayer( playerId, id );
		isPlayerShowed[playerId] = true;
	}

	@Override
	public void hide( IPlayer player )
	{
		if( isDestroyed() ) return;
		if( player.isOnline() == false ) return;
		
		int playerId = player.getId();
		
		SampNativeFunction.textDrawHideForPlayer( playerId, id );
		isPlayerShowed[playerId] = false;
	}

	@Override
	public void showForAll()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawShowForAll( id );
		for( int i=0; i<isPlayerShowed.length; i++ ) isPlayerShowed[i] = true;
	}

	@Override
	public void hideForAll()
	{
		if( isDestroyed() ) return;
		
		SampNativeFunction.textDrawHideForAll( id );
		for( int i=0; i<isPlayerShowed.length; i++ ) isPlayerShowed[i] = false;
	}
}
