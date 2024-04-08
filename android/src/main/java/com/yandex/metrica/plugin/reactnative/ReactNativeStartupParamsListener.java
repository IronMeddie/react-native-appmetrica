/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

package com.yandex.metrica.plugin.reactnative;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import io.appmetrica.analytics.StartupParamsCallback;

public class ReactNativeStartupParamsListener implements StartupParamsCallback {

    @NonNull
    private final Callback listener;

    ReactNativeStartupParamsListener(@NonNull Callback listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(@Nullable Result result) {
        listener.invoke(toParamsMap(result), null);
    }

    @Override
    public void onRequestError(@NonNull Reason reason, @Nullable Result result) {
        listener.invoke(null, reason.toString());
    }

    private static WritableMap toParamsMap(@Nullable Result result){
        if (result == null) return null;
        WritableMap map = new WritableNativeMap();
        map.putString("deviceId", result.deviceId);
        map.putString("deviceIdHash", result.deviceIdHash);
        map.putString("uuid", result.uuid);
        return map;
    }
}
