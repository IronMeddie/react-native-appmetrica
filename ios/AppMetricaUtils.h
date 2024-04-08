/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

#import <CoreLocation/CoreLocation.h>
#import <AppMetricaCore/AppMetricaCore.h>
#import <AppMetricaCrashes/AppMetricaCrashes.h>

@interface AppMetricaUtils : NSObject

+ (AMAAppMetricaConfiguration *)configurationForDictionary:(NSDictionary *)configDict;
+ (AMAAppMetricaCrashesConfiguration *)crashConfigurationForDictionary:(NSDictionary *)crashConfigDict;
+ (CLLocation *)locationForDictionary:(NSDictionary *)locationDict;
+ (AMAECommerceScreen *)ecommerceScreenForDict:(NSDictionary *)ecommerceScreenDict;
+ (AMAECommerceAmount *)ecommerceAmountForDict:(NSDictionary *)ecommerceAmountDict;
+ (AMAECommercePrice *)ecommercePriceForDict:(NSDictionary *)ecommercePriceDict;
+ (AMAECommerceReferrer *)ecommerceReferrerForDict:(NSDictionary *)ecommerceReferrerDict;
+ (AMAECommerceProduct *)ecommerceProductForDict:(NSDictionary *)ecommerceProductDict;
+ (AMAECommerceCartItem *)ecommerceCartItemForDict:(NSDictionary *)ecommerceCartItemDict;
+ (AMAECommerceOrder *)ecommerceOrderForDict:(NSDictionary *)ecommerceOrderDict;
+ (AMAECommerce *)ecommerceForDict:(NSDictionary *)ecommerceDict;

@end
