import { NativeModules, Platform, AppState, Linking } from 'react-native';
import type { ECommerceEvent }  from './ecommerce.ts';

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

type AppMetricaConfig = {
  apiKey: string;
  appVersion?: string;
  crashReporting?: boolean;
  firstActivationAsUpdate?: boolean;
  location: Location;
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

type PreloadInfo = {
  trackingId: string;
  additionalInfo?: Object;
};

type Location = {
  latitude: number;
  longitude: number;
  altitude?: number;
  accuracy?: number;
  course?: number;
  speed?: number;
  timestamp?: number;
};

type StartupParamsReason = 'UNKNOWN' | 'NETWORK' | 'INVALID_RESPONSE';

export type StartupParams = {
  deviceIdHash?: string;
  deviceId?: string;
  uuid?: string;
};

type StartupParamsCallback = (params: StartupParams, reason?: StartupParamsReason) => void;

const DEVICE_ID_HASH_KEY = 'appmetrica_device_id_hash';
const DEVICE_ID_KEY = 'appmetrica_device_id';
const UUID_KEY = 'appmetrica_uuid';

function appOpenTracking(){
  const getUrlAsync = async () => {
    const initialUrl = await Linking.getInitialURL();
    if(initialUrl != null){
      AppMetrica.reportAppOpen(initialUrl);
    }
  };
  getUrlAsync();
};

export default {
  activate(config: AppMetricaConfig) {
    AppMetrica.activate(config);
    if (AppState.currentState === 'active' && config.sessionsAutoTracking !== false) {
      AppMetrica.resumeSession();
    }
    if (config.appOpenTrackingEnbled !== false) {
      appOpenTracking();
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

  reportError(identifier: string, message: string, _reason: Object) {
    AppMetrica.reportError(identifier, message);
  },

  reportEvent(eventName: string, attributes?: Object) {
    AppMetrica.reportEvent(eventName, attributes);
  },

  requestStartupParams(listener: StartupParamsCallback, identifiers: Array<string>) {
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

  reportECommerce(event: ECommerceEvent){
    AppMetrica.reportECommerce(event)
  },

  DEVICE_ID_HASH_KEY,

  DEVICE_ID_KEY,
  
  UUID_KEY
};
