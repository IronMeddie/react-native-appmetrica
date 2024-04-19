/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

package com.yandex.metrica.plugin.reactnative;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import io.appmetrica.analytics.AppMetricaConfig;
import io.appmetrica.analytics.PreloadInfo;
import io.appmetrica.analytics.ecommerce.ECommerceAmount;
import io.appmetrica.analytics.ecommerce.ECommerceCartItem;
import io.appmetrica.analytics.ecommerce.ECommerceEvent;
import io.appmetrica.analytics.ecommerce.ECommerceOrder;
import io.appmetrica.analytics.ecommerce.ECommercePrice;
import io.appmetrica.analytics.ecommerce.ECommerceProduct;
import io.appmetrica.analytics.ecommerce.ECommerceReferrer;
import io.appmetrica.analytics.ecommerce.ECommerceScreen;
import io.appmetrica.analytics.StartupParamsCallback;
import io.appmetrica.analytics.AdRevenue;
import io.appmetrica.analytics.Revenue;
import io.appmetrica.analytics.AdType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Currency;
import java.math.BigDecimal;

abstract class Utils {

    @NonNull
    static AppMetricaConfig toAppMetricaConfig(@NonNull ReadableMap configMap) {
        AppMetricaConfig.Builder builder = AppMetricaConfig.newConfigBuilder(configMap.getString("apiKey"));

        if (configMap.hasKey("appVersion")) {
            builder.withAppVersion(configMap.getString("appVersion"));
        }
        if (configMap.hasKey("crashReporting")) {
            builder.withCrashReporting(configMap.getBoolean("crashReporting"));
        }
        if (configMap.hasKey("firstActivationAsUpdate")) {
            builder.handleFirstActivationAsUpdate(configMap.getBoolean("firstActivationAsUpdate"));
        }
        if (configMap.hasKey("location")) {
            builder.withLocation(toLocation(configMap.getMap("location")));
        }
        if (configMap.hasKey("locationTracking")) {
            builder.withLocationTracking(configMap.getBoolean("locationTracking"));
        }
        if (configMap.hasKey("logs") && configMap.getBoolean("logs")) {
            builder.withLogs();
        }
        if (configMap.hasKey("maxReportsInDatabaseCount")) {
            builder.withMaxReportsInDatabaseCount(configMap.getInt("maxReportsInDatabaseCount"));
        }
        if (configMap.hasKey("nativeCrashReporting")) {
            builder.withNativeCrashReporting(configMap.getBoolean("nativeCrashReporting"));
        }
        if (configMap.hasKey("preloadInfo")) {
            builder.withPreloadInfo(toPreloadInfo(configMap.getMap("preloadInfo")));
        }
        if (configMap.hasKey("sessionTimeout")) {
            builder.withSessionTimeout(configMap.getInt("sessionTimeout"));
        }
        if (configMap.hasKey("statisticsSending")) {
            builder.withDataSendingEnabled(configMap.getBoolean("statisticsSending"));
        }
        if (configMap.hasKey("sessionsAutoTracking")) {
            builder.withSessionsAutoTrackingEnabled(configMap.getBoolean("sessionsAutoTracking"));
        }

        return builder.build();
    }

    @Nullable
    static Location toLocation(@Nullable ReadableMap locationMap) {
        if (locationMap == null) {
            return null;
        }

        Location location = new Location("Custom");

        if (locationMap.hasKey("latitude")) {
            location.setLatitude(locationMap.getDouble("latitude"));
        }
        if (locationMap.hasKey("longitude")) {
            location.setLongitude(locationMap.getDouble("longitude"));
        }
        if (locationMap.hasKey("altitude")) {
            location.setAltitude(locationMap.getDouble("altitude"));
        }
        if (locationMap.hasKey("accuracy")) {
            location.setAccuracy((float) locationMap.getDouble("accuracy"));
        }
        if (locationMap.hasKey("course")) {
            location.setBearing((float) locationMap.getDouble("course"));
        }
        if (locationMap.hasKey("speed")) {
            location.setSpeed((float) locationMap.getDouble("speed"));
        }
        if (locationMap.hasKey("timestamp")) {
            location.setTime((long) locationMap.getDouble("timestamp"));
        }

        return location;
    }

    @Nullable
    private static PreloadInfo toPreloadInfo(@Nullable ReadableMap preloadInfoMap) {
        if (preloadInfoMap == null) {
            return null;
        }

        PreloadInfo.Builder builder = PreloadInfo.newBuilder(preloadInfoMap.getString("trackingId"));

        if (preloadInfoMap.hasKey("additionalInfo")) {
            ReadableMap additionalInfo = preloadInfoMap.getMap("additionalInfo");
            if (additionalInfo != null) {
                for (Map.Entry<String, Object> entry : additionalInfo.toHashMap().entrySet()) {
                    Object value = entry.getValue();
                    builder.setAdditionalParams(entry.getKey(), value == null ? null : value.toString());
                }
            }
        }

        return builder.build();
    }

    @NonNull
    static ECommerceScreen toECommerceScreen(@NonNull ReadableMap ecommerceEventMap) {
        ECommerceScreen screen = new ECommerceScreen();
        if (ecommerceEventMap.hasKey("name")) {
            screen.setName(ecommerceEventMap.getString("name"));
        }
        if (ecommerceEventMap.hasKey("searchQuery")) {
            screen.setSearchQuery(ecommerceEventMap.getString("searchQuery"));
        }
        if (ecommerceEventMap.hasKey("payload")) {
            ReadableMap map = ecommerceEventMap.getMap("payload");
            if (map != null) {
                Map<String, Object> oldMap = map.toHashMap();
                Map<String, String> newMap = new HashMap<String, String>();
                for (Map.Entry<String, Object> entry : oldMap.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        newMap.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                screen.setPayload(newMap);
            }
        }
        if (ecommerceEventMap.hasKey("categoriesPath")) {
            ReadableArray list = ecommerceEventMap.getArray("categoriesPath");
            if (list != null) {
                List<String> newlist = new ArrayList<String>(list.size());
                for (Object object : list.toArrayList()) {
                    newlist.add(object.toString());
                }
                screen.setCategoriesPath(newlist);
            }
        }
        return screen;
    }


    @NonNull
    static ECommerceAmount toEcommerceAmount(@NonNull ReadableMap amountMap) {
        return new ECommerceAmount(amountMap.getDouble("amount"), Objects.requireNonNull(amountMap.getString("unit")));
    }

    @NonNull
    static ECommercePrice toECommercePrice(@NonNull ReadableMap priceMap) {
        ReadableMap amountMap = priceMap.getMap("amount");
        ECommerceAmount amount = toEcommerceAmount(Objects.requireNonNull(amountMap));
        ECommercePrice price = new ECommercePrice(amount);
        if (priceMap.hasKey("internalComponents")) {
            ReadableArray list = priceMap.getArray("internalComponents");
            if (list != null) {
                List<ECommerceAmount> newlist = new ArrayList<ECommerceAmount>(list.size());
                for (int i = 0; i < list.size(); i++) {
                    ECommerceAmount component = toEcommerceAmount(list.getMap(i));
                    newlist.add(component);
                }
                price.setInternalComponents(newlist);
            }
        }
        return price;
    }

    @NonNull
    static ECommerceProduct toECommerceProduct(@NonNull ReadableMap productMap) {
        ECommerceProduct product = new ECommerceProduct(Objects.requireNonNull(productMap.getString("sku")));

        if (productMap.hasKey("name")) {
            product.setName(productMap.getString("name"));
        }
        if (productMap.hasKey("actualPrice")) {
            ReadableMap priceMap = productMap.getMap("actualPrice");
            if (priceMap != null) {
                product.setActualPrice(toECommercePrice(priceMap));
            }
        }
        if (productMap.hasKey("originalPrice")) {
            ReadableMap priceMap = productMap.getMap("originalPrice");
            if (priceMap != null) {
                product.setOriginalPrice(toECommercePrice(priceMap));
            }
        }
        if (productMap.hasKey("promocodes")) {
            ReadableArray array = productMap.getArray("promocodes");
            if (array != null) {
                List<String> newArray = new ArrayList<String>(array.size());
                for (int i = 0; i < array.size(); i++) {
                    newArray.add(array.getString(i));
                }
                product.setPromocodes(newArray);
            }
        }
        if (productMap.hasKey("categoriesPath")) {
            ReadableArray array = productMap.getArray("categoriesPath");
            if (array != null) {
                List<String> newArray = new ArrayList<String>(array.size());
                for (int i = 0; i < array.size(); i++) {
                    newArray.add(array.getString(i));
                }
                product.setCategoriesPath(newArray);
            }
        }
        if (productMap.hasKey("payload")) {
            ReadableMap map = productMap.getMap("payload");
            if (map != null) {
                Map<String, String> newMap = new HashMap<String, String>();
                for (Map.Entry<String, Object> entry : map.toHashMap().entrySet()) {
                    if (entry.getValue() instanceof String) {
                        newMap.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                product.setPayload(newMap);
            }
        }
        return product;
    }

    @Nullable
    static ECommerceReferrer toECommerceReferrer(@Nullable ReadableMap referrerMap) {
        if (referrerMap == null) {
            return null;
        }
        ECommerceReferrer referrer = new ECommerceReferrer();
        if (referrerMap.hasKey("type")) {
            referrer.setType(referrerMap.getString("type"));
        }
        if (referrerMap.hasKey("identifier")) {
            referrer.setIdentifier(referrerMap.getString("identifier"));
        }
        if (referrerMap.hasKey("screen")) {
            ReadableMap screenMap = referrerMap.getMap("screen");
            if (screenMap != null) {
                referrer.setScreen(toECommerceScreen(screenMap));
            }
        }
        return referrer;
    }

    @NonNull
    static ECommerceCartItem toECommerceCartItem(@NonNull ReadableMap cartItemMap) {
        ECommerceProduct product = toECommerceProduct(Objects.requireNonNull(cartItemMap.getMap("product")));
        ECommercePrice price = toECommercePrice(Objects.requireNonNull(cartItemMap.getMap("price")));
        double quantity = cartItemMap.getDouble("quantity");
        ECommerceCartItem item = new ECommerceCartItem(product, price, quantity);
        if (cartItemMap.hasKey("referrer")) {
            ReadableMap referrerMap = cartItemMap.getMap("referrer");
            if (referrerMap != null) {
                item.setReferrer(toECommerceReferrer(referrerMap));
            }
        }
        return item;
    }

    @NonNull
    static ECommerceOrder toECommerceOrder(@NonNull ReadableMap orderMap) {
        String orderId = orderMap.getString("orderId");
        ReadableArray list = orderMap.getArray("products");
        List<ECommerceCartItem> newlist = new ArrayList<ECommerceCartItem>(Objects.requireNonNull(list).size());
        for (int i = 0; i < list.size(); i++) {
            ECommerceCartItem component = toECommerceCartItem(list.getMap(i));
            newlist.add(component);
        }
        ECommerceOrder order = new ECommerceOrder(Objects.requireNonNull(orderId), newlist);
        if (orderMap.hasKey("payload")) {
            ReadableMap map = orderMap.getMap("payload");
            if (map != null) {
                Map<String, String> newMap = new HashMap<String, String>();
                for (Map.Entry<String, Object> entry : map.toHashMap().entrySet()) {
                    if (entry.getValue() instanceof String) {
                        newMap.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                order.setPayload(newMap);
            }
        }
        return order;
    }

    @Nullable
    static ECommerceEvent toECommerceEvent(@Nullable ReadableMap EcommerceEventMap) {
        if (EcommerceEventMap == null) {
            return null;
        }
        String type = EcommerceEventMap.getString("ecommerceEvent");
        if (type == null) return null;
        if (type.equals("showSceenEvent")) {
            return ECommerceEvent.showScreenEvent(toECommerceScreen(Objects.requireNonNull(EcommerceEventMap.getMap("ecommerceScreen"))));
        }
        if (type.equals("showProductCardEvent")) {
            return ECommerceEvent.showProductCardEvent(toECommerceProduct(Objects.requireNonNull(EcommerceEventMap.getMap("product"))), toECommerceScreen(Objects.requireNonNull(EcommerceEventMap.getMap("ecommerceScreen"))));
        }
        if (type.equals("showProductDetailsEvent")) {
            return ECommerceEvent.showProductDetailsEvent(toECommerceProduct(Objects.requireNonNull(EcommerceEventMap.getMap("product"))), toECommerceReferrer(EcommerceEventMap.getMap("referrer")));
        }
        if (type.equals("addCartItemEvent")) {
            return ECommerceEvent.addCartItemEvent(toECommerceCartItem(Objects.requireNonNull(EcommerceEventMap.getMap("cartItem"))));
        }
        if (type.equals("removeCartItemEvent")) {
            return ECommerceEvent.removeCartItemEvent(toECommerceCartItem(Objects.requireNonNull(EcommerceEventMap.getMap("cartItem"))));
        }
        if (type.equals("beginCheckoutEvent")) {
            return ECommerceEvent.beginCheckoutEvent(toECommerceOrder(Objects.requireNonNull(EcommerceEventMap.getMap("order"))));
        }
        if (type.equals("purchaseEvent")) {
            ECommerceOrder order = toECommerceOrder(Objects.requireNonNull(EcommerceEventMap.getMap("order")));
            return ECommerceEvent.purchaseEvent(order);
        }
        return null;
    }

    @NonNull
    static List<String> toStartupKeyList(@Nullable ReadableArray keys) {
        ArrayList<String> startupKeys = new ArrayList<>();
        if (keys != null) {
            for (int i = 0; i < keys.size(); i++) {
                String item = keys.getString(i);
                if (item.equals("appmetrica_device_id_hash")) {
                    startupKeys.add(StartupParamsCallback.APPMETRICA_DEVICE_ID_HASH);
                }
                if (item.equals("appmetrica_device_id")) {
                    startupKeys.add(StartupParamsCallback.APPMETRICA_DEVICE_ID);
                }
                if (item.equals("appmetrica_uuid")) {
                    startupKeys.add(StartupParamsCallback.APPMETRICA_UUID);
                }
            }
        }
        return startupKeys;
    }

    @NonNull
    static Revenue toRevenue(ReadableMap revenueMap) {
        long price = (long) revenueMap.getDouble("price") * 1000000;
        String currency = revenueMap.getString("currency");
        Revenue.Builder revenue = Revenue.newBuilder(price, Currency.getInstance(currency));
        if (revenueMap.hasKey("productID")) {
            revenue.withProductID(revenueMap.getString("productID"));
        }
        if (revenueMap.hasKey("payload")) {
            ReadableMap payloadMap = revenueMap.getMap("payload");
            if (payloadMap != null) {
                revenue.withPayload(payloadMap.toString());
            }
        }
        if (revenueMap.hasKey("quantity")) {
            revenue.withQuantity(revenueMap.getInt("quantity"));
        }
        revenue.withReceipt(toReceipt(revenueMap));
        return revenue.build();
    }

    @NonNull
    static Revenue.Receipt toReceipt(ReadableMap receipt) {
        Revenue.Receipt.Builder revenueReceipt = Revenue.Receipt.newBuilder();
        if (receipt.hasKey("receiptData")) {
            revenueReceipt.withData(receipt.getString("receiptData"));
        }
        if (receipt.hasKey("signature")) {
            revenueReceipt.withSignature(receipt.getString("signature"));
        }
        return revenueReceipt.build();
    }

    @NonNull
    static AdRevenue toAdRevenue(ReadableMap revenueMap) {
        double price = revenueMap.getDouble("price");   
        String currency = revenueMap.getString("currency");
        AdRevenue.Builder adRevenue = AdRevenue.newBuilder(new BigDecimal(price), Currency.getInstance(currency));
        if (revenueMap.hasKey("payload")) {
            ReadableMap payloadMap = revenueMap.getMap("payload");
            if (payloadMap != null) {
                Map<String, Object> map = payloadMap.toHashMap();
                Map<String, String> adRevenuePayload = new HashMap<>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        adRevenuePayload.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                adRevenue.withPayload(adRevenuePayload);
            }
        }
        if (revenueMap.hasKey("adType")) {
            String type = revenueMap.getString("adType");
            if (type != null) {
                adRevenue.withAdType(toAdType(type));
            }
        }
        if (revenueMap.hasKey("adNetwork")) {
            adRevenue.withAdNetwork(revenueMap.getString("adNetwork"));
        }
        if (revenueMap.hasKey("adPlacementID")) {
            adRevenue.withAdPlacementId(revenueMap.getString("adPlacementID"));
        }
        if (revenueMap.hasKey("adPlacementName")) {
            adRevenue.withAdPlacementName(revenueMap.getString("adPlacementName"));
        }
        if (revenueMap.hasKey("adUnitID")) {
            adRevenue.withAdUnitId(revenueMap.getString("adUnitID"));
        }
        if (revenueMap.hasKey("adUnitName")) {
            adRevenue.withAdUnitName(revenueMap.getString("adUnitName"));
        }
        if (revenueMap.hasKey("precision")) {
            adRevenue.withPrecision(revenueMap.getString("precision"));
        }
        return adRevenue.build();
    }

    @NonNull
    static AdType toAdType(String type) {
        switch (type) {
            case "native": return AdType.NATIVE;
            case "banner": return AdType.BANNER;
            case "mrec": return AdType.MREC;
            case "interstitial": return AdType.INTERSTITIAL;
            case "rewarded": return AdType.REWARDED;
            default: return AdType.OTHER;
        }
    }

    @NonNull
    static Boolean isSessionTrackingEnabled(@NonNull ReadableMap configMap) {
        if (configMap.hasKey("sessionsAutoTracking")) {
            return configMap.getBoolean("sessionsAutoTracking");
        }
        return true;
    }
}
