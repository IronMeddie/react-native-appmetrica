/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

#import "AppMetrica.h"
#import "AppMetricaUtils.h"

static NSString *const kYMMReactNativeExceptionName = @"ReactNativeException";

@implementation AppMetrica

@synthesize methodQueue = _methodQueue;

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(activate:(NSDictionary *)configDict)
{
    [AMAAppMetrica activateWithConfiguration:[AppMetricaUtils configurationForDictionary:configDict]];
}

RCT_EXPORT_METHOD(getLibraryApiLevel)
{
    // It does nothing for iOS
}

RCT_EXPORT_METHOD(getLibraryVersion:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([AMAAppMetrica libraryVersion]);
}

RCT_EXPORT_METHOD(pauseSession)
{
    [AMAAppMetrica pauseSession];
}

RCT_EXPORT_METHOD(reportAppOpen:(NSString *)deeplink)
{
    [AMAAppMetrica trackOpeningURL:[NSURL URLWithString:deeplink]];
}

RCT_EXPORT_METHOD(reportError:(NSDictionary *)error) {
    [[AMAAppMetricaCrashes crashes] reportError:[AppMetricaUtils errorForDict:error] onFailure:nil];
}

RCT_EXPORT_METHOD(reportEvent:(NSString *)eventName:(NSDictionary *)attributes)
{
    if (attributes == nil) {
        [AMAAppMetrica reportEvent:eventName onFailure:^(NSError *error) {
            NSLog(@"error: %@", [error localizedDescription]);
        }];
    } else {
        [AMAAppMetrica reportEvent:eventName parameters:attributes onFailure:^(NSError *error) {
            NSLog(@"error: %@", [error localizedDescription]);
        }];
    }
}

RCT_EXPORT_METHOD(requestStartupParams:(RCTResponseSenderBlock)listener)
{
    AMAIdentifiersCompletionBlock block = ^(NSDictionary<AMAStartupKey,id> * _Nullable identifiers, NSError * _Nullable error) {
        listener(@[[self wrap:identifiers], [self wrap:[AppMetricaUtils stringFromRequestDeviceIDError:error]]]);
    };

    [AMAAppMetrica requestStartupIdentifiersWithCompletionQueue:nil completionBlock:block];
    
    //    NSArray<AMAStartupKey> *keys = @[kAMADeviceIDHashKey, kAMADeviceIDKey, kAMAUUIDKey];
    //    [AMAAppMetrica requestStartupIdentifiers:keys completionQueue:nil completionBlock:block];
}

RCT_EXPORT_METHOD(resumeSession)
{
    [AMAAppMetrica resumeSession];
}

RCT_EXPORT_METHOD(sendEventsBuffer)
{
    [AMAAppMetrica sendEventsBuffer];
}

RCT_EXPORT_METHOD(setLocation:(NSDictionary *)locationDict)
{
    AMAAppMetrica.customLocation = [AppMetricaUtils locationForDictionary:locationDict];
}

RCT_EXPORT_METHOD(setLocationTracking:(BOOL)enabled)
{
    AMAAppMetrica.locationTrackingEnabled = enabled;
}

RCT_EXPORT_METHOD(setStatisticsSending:(BOOL)enabled)
{
    [AMAAppMetrica setDataSendingEnabled:enabled];
}

RCT_EXPORT_METHOD(reportECommerce:(NSDictionary *)ecommerceDict)
{
   [AMAAppMetrica reportECommerce:[AppMetricaUtils ecommerceForDict:ecommerceDict] onFailure:nil];
}

RCT_EXPORT_METHOD(setUserProfileID:(NSString *)userProfileID)
{
    [AMAAppMetrica setUserProfileID:userProfileID];
}

- (NSObject *)wrap:(NSObject *)value
{
    if (value == nil) {
        return [NSNull null];
    }
    return value;
}

@end
