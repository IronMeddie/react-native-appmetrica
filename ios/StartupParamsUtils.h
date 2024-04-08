/*
 * Version for React Native
 * © 2020 YANDEX
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://yandex.com/legal/appmetrica_sdk_agreement/
 */

@interface StartupParamsUtils : NSObject

+ (NSArray *)toStartupKeys:(NSArray *)keys;
+ (NSDictionary *)toStrartupParamsResult:(NSDictionary *)resultDict;
+ (NSString *)stringFromRequestStartupParamsError:(NSError *)error;

@end
