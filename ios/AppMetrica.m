/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

#import "AppMetrica.h"
#import "AppMetricaUtils.h"
#import "StartupParamsUtils.h"
#import <AppMetricaCrashes/AppMetricaCrashes.h>

static NSString *const kYMMReactNativeExceptionName = @"ReactNativeException";

@implementation AppMetrica

@synthesize methodQueue = _methodQueue;

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(activate:(NSDictionary *)configDict)
{
    [[AMAAppMetricaCrashes crashes] setConfiguration:[AppMetricaUtils crashConfigurationForDictionary:configDict]];
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

RCT_EXPORT_METHOD(reportError:(NSString *)identifier:(NSString *)message) {
    AMAError *error = [AMAError errorWithIdentifier:identifier message:message parameters:NULL];
    [[AMAAppMetricaCrashes crashes] reportError:error onFailure:nil];
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

RCT_EXPORT_METHOD(requestStartupParams:(NSArray *)identifiers:(RCTResponseSenderBlock)listener)
{
    AMAIdentifiersCompletionBlock block = ^(NSDictionary<AMAStartupKey,id> * _Nullable identifiers, NSError * _Nullable error) {
        NSDictionary *result = [StartupParamsUtils toStrartupParamsResult:identifiers];
        listener(@[result, [self wrap:[StartupParamsUtils stringFromRequestStartupParamsError:error]]]);
    };
    [AMAAppMetrica requestStartupIdentifiersWithKeys:[StartupParamsUtils toStartupKeys:identifiers] completionQueue:nil completionBlock:block];
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

RCT_EXPORT_METHOD(setDataSendingEnabled:(BOOL)enabled)
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

RCT_EXPORT_METHOD(reportRevenue:(NSDictionary *)revenueDict)
{
    [AMAAppMetrica reportRevenue:[AppMetricaUtils revenueForDict:revenueDict] onFailure:nil];
}

RCT_EXPORT_METHOD(reportAdRevenue:(NSDictionary *)revenueDict)
{
    [AMAAppMetrica reportAdRevenue:[AppMetricaUtils adRevenueForDict:revenueDict] onFailure:nil];
}

- (NSObject *)wrap:(NSObject *)value
{
    if (value == nil) {
        return [NSNull null];
    }
    return value;
}

@end
