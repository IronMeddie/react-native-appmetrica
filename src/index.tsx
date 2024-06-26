import { NativeModules, Platform, Linking } from 'react-native';
import type { ECommerceEvent }  from './ecommerce';
import type { Revenue, AdRevenue, AdType }  from './revenue';

const LINKING_ERROR =
  `The package 'react-native-appmetrica' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const AppMetrica = NativeModules.AppMetrica
  ? NativeModules.AppMetrica
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

var activated = false;

function appOpenTracking() {
  const getUrlAsync = async () => {
    const initialUrl = await Linking.getInitialURL();
    if(initialUrl != null) {
      AppMetrica.reportAppOpen(initialUrl);
    }
  };
  const callback = (event: { url: string; }) => {
    AppMetrica.reportAppOpen(event.url);
  }
  getUrlAsync();
  Linking.addEventListener('url', callback);
};

export default {
  activate(config: AppMetricaConfig) {
    if (!activated) {
      AppMetrica.activate(config);
      if (config.appOpenTrackingEnbled !== false) {
        appOpenTracking();
      }
      activated = true;
    }
  },

  // Android only
  async getLibraryApiLevel(): Promise<number> {
    return AppMetrica.getLibraryApiLevel();
  },

  async getLibraryVersion(): Promise<string> {
    return AppMetrica.getLibraryVersion();
  },

  pauseSession() {
    AppMetrica.pauseSession();
  },

  reportAppOpen(deeplink?: string) {
    AppMetrica.reportAppOpen(deeplink);
  },

  reportError(identifier: string, message: string, _reason?: Object) {
    AppMetrica.reportError(identifier, message);
  },

  reportEvent(eventName: string, attributes?: Object) {
    AppMetrica.reportEvent(eventName, attributes);
  },

  requestStartupParams(listener: StartupParamsCallback, identifiers: Array<StartupParamsKeys>) {
    AppMetrica.requestStartupParams(identifiers, listener);
  },

  resumeSession() {
    AppMetrica.resumeSession();
  },

  sendEventsBuffer() {
    AppMetrica.sendEventsBuffer();
  },

  setLocation(location?: Location) {
    AppMetrica.setLocation(location);
  },

  setLocationTracking(enabled: boolean) {
    AppMetrica.setLocationTracking(enabled);
  },

  setDataSendingEnabled(enabled: boolean) {
    AppMetrica.setDataSendingEnabled(enabled);
  },

  setUserProfileID(userProfileID?: string) {
    AppMetrica.setUserProfileID(userProfileID);
  },

  reportECommerce(event: ECommerceEvent) {
    AppMetrica.reportECommerce(event)
  },

  reportRevenue(revenue: Revenue) {
    AppMetrica.reportRevenue(revenue);
  },

  reportAdRevenue(adRevenue: AdRevenue) {
    AppMetrica.reportAdRevenue(adRevenue);
  }
};

export type AppMetricaConfig = {
  apiKey: string;
  appVersion?: string;
  crashReporting?: boolean;
  firstActivationAsUpdate?: boolean;
  location?: Location;
  locationTracking?: boolean;
  logs?: boolean;
  sessionTimeout?: number;
  statisticsSending?: boolean;
  preloadInfo?: PreloadInfo;
  maxReportsInDatabaseCount?: number; // Android only
  nativeCrashReporting?: boolean; // Android only
  activationAsSessionStart?: boolean; // iOS only
  sessionsAutoTracking?: boolean; // iOS only
  appOpenTrackingEnbled?: boolean;
};
  
export type PreloadInfo = {
  trackingId: string;
  additionalInfo?: Object;
};
  
export type Location = {
  latitude: number;
  longitude: number;
  altitude?: number;
  accuracy?: number;
  course?: number;
  speed?: number;
  timestamp?: number;
};
  
export type StartupParamsReason = 'UNKNOWN' | 'NETWORK' | 'INVALID_RESPONSE';

export type StartupParams = {
  deviceIdHash?: string;
  deviceId?: string;
  uuid?: string;
};
  
export type StartupParamsCallback = (params: StartupParams, reason?: StartupParamsReason) => void;

export enum StartupParamsKeys {
  DEVICE_ID_HASH_KEY = 'appmetrica_device_id_hash',
  DEVICE_ID_KEY = 'appmetrica_device_id',
  UUID_KEY = 'appmetrica_uuid'
}
