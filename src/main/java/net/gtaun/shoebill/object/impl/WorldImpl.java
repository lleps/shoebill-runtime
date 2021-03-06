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

package net.gtaun.shoebill.object.impl;

import net.gtaun.shoebill.SampNativeFunction;
import net.gtaun.shoebill.SampObjectStoreImpl;
import net.gtaun.shoebill.constant.PlayerMarkerMode;
import net.gtaun.shoebill.constant.WeaponModel;
import net.gtaun.shoebill.data.*;
import net.gtaun.shoebill.object.World;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author MK124
 */
public class WorldImpl implements World {
    private final SampObjectStoreImpl store;

    private float nameTagDrawDistance = 70;
    private float chatRadius = -1;
    private float playerMarkerRadius = -1;
    private int weatherId = 10;


    public WorldImpl(SampObjectStoreImpl store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).toString();
    }

    @Override
    public void setTeamCount(int count) {
        SampNativeFunction.setTeamCount(count);
    }

    @Override
    public int addPlayerClass(int modelId, float x, float y, float z, float angle, int weapon1, int ammo1, int weapon2, int ammo2, int weapon3, int ammo3) {
        int id = SampNativeFunction.addPlayerClass(modelId, x, y, z, angle, weapon1, ammo1, weapon2, ammo2, weapon3, ammo3);
        store.setPlayerClass(id, new SpawnInfo(x, y, z, 0, 0, angle, modelId, 0, WeaponModel.get(weapon1), ammo1, WeaponModel.get(weapon2), ammo2, WeaponModel.get(weapon3), ammo3));
        return id;
    }

    @Override
    public int addPlayerClass(int teamId, int modelId, float x, float y, float z, float angle, int weapon1, int ammo1, int weapon2, int ammo2, int weapon3, int ammo3) {
        int id = SampNativeFunction.addPlayerClassEx(teamId, modelId, x, y, z, angle, weapon1, ammo1, weapon2, ammo2, weapon3, ammo3);
        store.setPlayerClass(id, new SpawnInfo(x, y, z, 0, 0, angle, modelId, teamId, WeaponModel.get(weapon1), ammo1, WeaponModel.get(weapon2), ammo2, WeaponModel.get(weapon3), ammo3));
        return id;
    }

    @Override
    public int addPlayerClass(int modelId, Vector3D position, float angle, WeaponModel weapon1, int ammo1, WeaponModel weapon2, int ammo2, WeaponModel weapon3, int ammo3) {
        int id = SampNativeFunction.addPlayerClass(modelId, position.x, position.y, position.z, angle, weapon1.getId(), ammo1, weapon2.getId(), ammo2, weapon3.getId(), ammo3);
        store.setPlayerClass(id, new SpawnInfo(position, 0, 0, angle, modelId, 0, weapon1, ammo1, weapon2, ammo2, weapon3, ammo3));
        return id;
    }

    @Override
    public int addPlayerClass(int teamId, int modelId, Vector3D position, float angle, WeaponModel weapon1, int ammo1, WeaponModel weapon2, int ammo2, WeaponModel weapon3, int ammo3) {
        int id = SampNativeFunction.addPlayerClassEx(teamId, modelId, position.x, position.y, position.z, angle, weapon1.getId(), ammo1, weapon2.getId(), ammo2, weapon3.getId(), ammo3);
        store.setPlayerClass(id, new SpawnInfo(position, 0, 0, angle, modelId, teamId, weapon1, ammo1, weapon2, ammo2, weapon3, ammo3));
        return id;
    }

    @Override
    public int addPlayerClass(int modelId, Vector3D position, float angle, WeaponData weapon1, WeaponData weapon2, WeaponData weapon3) {
        int id = SampNativeFunction.addPlayerClass(modelId, position.x, position.y, position.z, angle, weapon1.getModel().getId(), weapon1.getAmmo(), weapon2.getModel().getId(), weapon2.getAmmo(), weapon3.getModel().getId(), weapon3.getAmmo());
        store.setPlayerClass(id, new SpawnInfo(position, 0, 0, angle, modelId, 0, weapon1.model, weapon1.ammo, weapon2.model, weapon2.ammo, weapon3.model, weapon3.ammo));
        return id;
    }

    @Override
    public int addPlayerClass(int teamId, int modelId, Vector3D position, float angle, WeaponData weapon1, WeaponData weapon2, WeaponData weapon3) {
        int id = SampNativeFunction.addPlayerClassEx(teamId, modelId, position.x, position.y, position.z, angle, weapon1.getModel().getId(), weapon1.getAmmo(), weapon2.getModel().getId(), weapon2.getAmmo(), weapon3.getModel().getId(), weapon3.getAmmo());
        store.setPlayerClass(id, new SpawnInfo(position, 0, 0, angle, modelId, teamId, weapon1.model, weapon1.ammo, weapon2.model, weapon2.ammo, weapon3.model, weapon3.ammo));
        return id;
    }

    @Override
    public int addPlayerClass(SpawnInfo spawnInfo) {
        AngledLocation loc = spawnInfo.getLocation();
        WeaponData weapon1 = spawnInfo.getWeapon1();
        WeaponData weapon2 = spawnInfo.getWeapon2();
        WeaponData weapon3 = spawnInfo.getWeapon3();
        int id = SampNativeFunction.addPlayerClassEx(spawnInfo.getTeamId(), spawnInfo.getSkinId(), loc.getX(), loc.getY(), loc.getZ(), loc.getAngle(), weapon1.getModel().getId(), weapon1.getAmmo(), weapon2.getModel().getId(), weapon2.getAmmo(), weapon3.getModel().getId(), weapon3.getAmmo());
        store.setPlayerClass(id, spawnInfo);
        return id;
    }

    @Override
    public float getChatRadius() {
        return chatRadius;
    }

    @Override
    public void setChatRadius(float radius) {
        SampNativeFunction.limitGlobalChatRadius(radius);
    }

    @Override
    public float getPlayerMarkerRadius() {
        return playerMarkerRadius;
    }

    @Override
    public void setPlayerMarkerRadius(float radius) {
        SampNativeFunction.limitPlayerMarkerRadius(radius);
    }

    @Override
    public int getWeather() {
        return weatherId;
    }

    @Override
    public void setWeather(int weatherid) {
        SampNativeFunction.setWeather(weatherid);
        this.weatherId = weatherid;
    }

    @Override
    public float getGravity() {
        return Float.parseFloat(SampNativeFunction.getConsoleVarAsString("gravity"));
    }

    @Override
    public void setGravity(float gravity) {
        SampNativeFunction.setGravity(gravity);
    }

    @Override
    public void setWorldTime(int hour) {
        SampNativeFunction.setWorldTime(hour);
    }

    @Override
    public float getNameTagDrawDistance() {
        return nameTagDrawDistance;
    }

    @Override
    public void setNameTagDrawDistance(float distance) {
        nameTagDrawDistance = distance;
        SampNativeFunction.setNameTagDrawDistance(distance);
    }

    @Override
    public void showNameTags(boolean enabled) {
        SampNativeFunction.showNameTags(enabled);
    }

    @Override
    public void showPlayerMarkers(PlayerMarkerMode mode) {
        SampNativeFunction.showPlayerMarkers(mode.getValue());
    }

    @Override
    public void enableTirePopping(boolean enabled) {
        SampNativeFunction.enableTirePopping(enabled);
    }

    @Override
    public void enableVehicleFriendlyFire() {
        SampNativeFunction.enableVehicleFriendlyFire();
    }

    @Override
    public void allowInteriorWeapons(boolean allow) {
        SampNativeFunction.allowInteriorWeapons(allow);
    }

    @Override
    public void createExplosion(Location location, int type, float radius) {
        SampNativeFunction.createExplosion(location.getX(), location.getY(), location.getZ(), type, radius);
    }

    @Override
    public void enableZoneNames(boolean enabled) {
        SampNativeFunction.enableZoneNames(enabled);
    }

    @Override
    public void usePlayerPedAnims() {
        SampNativeFunction.usePlayerPedAnims();
    }

    @Override
    public void disableInteriorEnterExits() {
        SampNativeFunction.disableInteriorEnterExits();
    }

    @Override
    public void disableNameTagLOS() {
        SampNativeFunction.disableNameTagLOS();
    }

    @Override
    public void enableStuntBonusForAll(boolean enabled) {
        SampNativeFunction.enableStuntBonusForAll(enabled);
    }

    @Override
    public void manualEngineAndLights() {
        SampNativeFunction.manualVehicleEngineAndLights();
    }

    @Override
    public void setObjectsDefaultCameraCol(boolean disable) {
        SampNativeFunction.setObjectsDefaultCameraCol(disable ? 1 : 0);
    }
}
