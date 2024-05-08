package io.appmetrica.analytics.reactnative;

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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Currency;

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

    static ECommerceScreen toECommerceScreen(ReadableMap ecommerceEventMap) {
        ECommerceScreen screen = new ECommerceScreen();
        if (ecommerceEventMap.hasKey("name")) {
            screen.setName(ecommerceEventMap.getString("name"));
        }
        if (ecommerceEventMap.hasKey("searchQuery")) {
            screen.setSearchQuery(ecommerceEventMap.getString("searchQuery"));
        }
        if (ecommerceEventMap.hasKey("payload")) {
            Map<String, Object> map = ecommerceEventMap.getMap("payload").toHashMap();
            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                }
            }
            screen.setPayload(newMap);
        }
        if (ecommerceEventMap.hasKey("categoriesPath")) {
            List<Object> list = ecommerceEventMap.getArray("categoriesPath").toArrayList();
            List<String> newlist = new ArrayList<String>(list.size());
            for (Object object : list) {
                newlist.add(Objects.toString(object, null));
            }
            screen.setCategoriesPath(newlist);
        }
        return screen;
    }


    static ECommerceAmount toEcommerceAmount(ReadableMap amountMap) {
        if (amountMap == null) {
            return null;
        }
        return new ECommerceAmount(amountMap.getDouble("amount"), amountMap.getString("unit"));
    }

    static ECommercePrice toECommercePrice(ReadableMap priceMap) {
        ECommercePrice price = new ECommercePrice(toEcommerceAmount(priceMap.getMap("amount")));
        if (priceMap.hasKey("internalComponents")) {
            ReadableArray list = priceMap.getArray("internalComponents");
            List<ECommerceAmount> newlist = new ArrayList<ECommerceAmount>(list.size());
            for (int i = 0; i < list.size(); i++) {
                ECommerceAmount component = toEcommerceAmount(list.getMap(i));
                newlist.add(component);
            }
            price.setInternalComponents(newlist);
        }
        return price;
    }

    static ECommerceProduct toECommerceProduct(ReadableMap productMap) {
        ECommerceProduct product = new ECommerceProduct(productMap.getString("sku"));

        if (productMap.hasKey("name")) {
            product.setName(productMap.getString("name"));
        }
        if (productMap.hasKey("actualPrice")) {
            product.setActualPrice(toECommercePrice(productMap.getMap("actualPrice")));
        }
        if (productMap.hasKey("originalPrice")) {
            product.setOriginalPrice(toECommercePrice(productMap.getMap("originalPrice")));
        }
        if (productMap.hasKey("promocodes")) {
            ReadableArray array = productMap.getArray("promocodes");
            List<String> newArray = new ArrayList<String>(array.size());
            for (int i = 0; i < array.size(); i++) {
                newArray.add(array.getString(i));
            }
            product.setPromocodes(newArray);
        }
        if (productMap.hasKey("categoriesPath")) {
            ReadableArray array = productMap.getArray("categoriesPath");
            List<String> newArray = new ArrayList<String>(array.size());
            for (int i = 0; i < array.size(); i++) {
                newArray.add(array.getString(i));
            }
            product.setCategoriesPath(newArray);
        }
        if (productMap.hasKey("payload")) {
            Map<String, Object> map = productMap.getMap("payload").toHashMap();
            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                }
            }
            product.setPayload(newMap);
        }

        return product;
    }

    static ECommerceReferrer toECommerceReferrer(ReadableMap referrerMap) {
        ECommerceReferrer referrer = new ECommerceReferrer();
        if (referrerMap.hasKey("type")) {
            referrer.setType(referrerMap.getString("type"));
        }
        if (referrerMap.hasKey("identifier")) {
            referrer.setIdentifier(referrerMap.getString("identifier"));
        }
        if (referrerMap.hasKey("screen")) {
            referrer.setScreen(toECommerceScreen(referrerMap.getMap("screen")));
        }
        return referrer;
    }

    static ECommerceCartItem toECommerceCartItem(ReadableMap cartItemMap) {
        ECommerceProduct product = toECommerceProduct(cartItemMap.getMap("product"));
        ECommercePrice price = toECommercePrice(cartItemMap.getMap("price"));
        Double quantity = cartItemMap.getDouble("quantity");
        ECommerceCartItem item = new ECommerceCartItem(product, price, quantity);
        if (cartItemMap.hasKey("referrer")) {
            item.setReferrer(toECommerceReferrer(cartItemMap.getMap("referrer")));
        }
        return item;
    }

    static ECommerceOrder toECommerceOrder(ReadableMap orderMap) {
        String orderId = orderMap.getString("orderId");
        ReadableArray list = orderMap.getArray("products");
        List<ECommerceCartItem> newlist = new ArrayList<ECommerceCartItem>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ECommerceCartItem component = toECommerceCartItem(list.getMap(i));
            newlist.add(component);
        }
        ECommerceOrder order = new ECommerceOrder(orderId, newlist);
        if (orderMap.hasKey("payload")) {
            Map<String, Object> map = orderMap.getMap("payload").toHashMap();
            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                }
            }
            order.setPayload(newMap);
        }
        return order;
    }

    static ECommerceEvent toECommerceEvent(ReadableMap EcommerceEventMap) {
        String type = EcommerceEventMap.getString("ecommerceEvent");
        if (type == null) return null;
        if (type.equals("showSceenEvent")) {
            return ECommerceEvent.showScreenEvent(toECommerceScreen(EcommerceEventMap.getMap("ecommerceScreen")));
        }
        if (type.equals("showProductCardEvent")) {
            return ECommerceEvent.showProductCardEvent(toECommerceProduct(EcommerceEventMap.getMap("product")), toECommerceScreen(EcommerceEventMap.getMap("ecommerceScreen")));
        }
        if (type.equals("showProductDetailsEvent")) {
            return ECommerceEvent.showProductDetailsEvent(toECommerceProduct(EcommerceEventMap.getMap("product")), toECommerceReferrer(EcommerceEventMap.getMap("referrer")));
        }
        if (type.equals("addCartItemEvent")) {
            return ECommerceEvent.addCartItemEvent(toECommerceCartItem(EcommerceEventMap.getMap("cartItem")));
        }
        if (type.equals("removeCartItemEvent")) {
            return ECommerceEvent.removeCartItemEvent(toECommerceCartItem(EcommerceEventMap.getMap("cartItem")));
        }
        if (type.equals("beginCheckoutEvent")) {
            return ECommerceEvent.beginCheckoutEvent(toECommerceOrder(EcommerceEventMap.getMap("order")));
        }
        if (type.equals("purchaseEvent")) {
            return ECommerceEvent.purchaseEvent(toECommerceOrder(EcommerceEventMap.getMap("order")));
        }
        return null;
    }

    static List<String> toStartupKeyList(ReadableArray keys) {
        ArrayList<String> startupKeys = new ArrayList<>();
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
        return startupKeys;
    }

    @NonNull
    static Revenue toRevenue(@NonNull ReadableMap revenueMap) {
        long price = (long) (revenueMap.getDouble("price") * 1000000);
        String currency = revenueMap.getString("currency");
        Revenue.Builder revenue = Revenue.newBuilder(price, Currency.getInstance(currency));
        if (revenueMap.hasKey("productID")) {
            revenue.withProductID(revenueMap.getString("productID"));
        }
        if (revenueMap.hasKey("payload")) {
            revenue.withPayload(revenueMap.getString("payload"));
        }
        if (revenueMap.hasKey("quantity")) {
            revenue.withQuantity(revenueMap.getInt("quantity"));
        }
        revenue.withReceipt(toReceipt(revenueMap));
        return revenue.build();
    }

    @NonNull
    static Revenue.Receipt toReceipt(@NonNull ReadableMap receipt) {
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
    static AdRevenue toAdRevenue(@NonNull ReadableMap revenueMap) {
        double price = revenueMap.getDouble("price");   
        String currency = revenueMap.getString("currency");
        AdRevenue.Builder adRevenue = AdRevenue.newBuilder(price, Currency.getInstance(currency));
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
    static AdType toAdType(@NonNull String type) {
        switch (type) {
            case "native": return AdType.NATIVE;
            case "banner": return AdType.BANNER;
            case "mrec": return AdType.MREC;
            case "interstitial": return AdType.INTERSTITIAL;
            case "rewarded": return AdType.REWARDED;
            default: return AdType.OTHER;
        }
    }
}
