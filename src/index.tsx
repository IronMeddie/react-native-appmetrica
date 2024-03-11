import { NativeModules, Platform, AppState, Linking } from 'react-native';
import ECommerceClass, { ECommerceEvent }  from './ecommerce.ts';

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

type Error = {
  identifier:string;
  message:string;
};

type StartupParamsReason = 'UNKNOWN' | 'NETWORK' | 'INVALID_RESPONSE';

export type StartupParams = {
  appmetrica_deviceIDHash: string;
  appmetrica_deviceID: string;
  appmetrica_uuid: string;
};

type StartupParamsCallback = (params: StartupParams, reason?: StartupParamsReason) => void;

const ECommerce = ECommerceClass;

function sessionTracking(){
  AppState.addEventListener('change', state => {
  if (AppState.currentState === 'active') {
  AppMetrica.resumeSession();
  }
    if(state === 'active'){
      AppMetrica.resumeSession();
    };
    if(state === 'background'){
      AppMetrica.pauseSession();
    };
  });
};

function appOpenTracking(){
  const getUrlAsync = async () => {
    const initialUrl = await Linking.getInitialURL();
    console.debug("AppMetrica initialUrl: " + initialUrl);
    if(initialUrl != null){
      AppMetrica.reportAppOpen(initialUrl);
    }
  };
  getUrlAsync();
};

export default {
  activate(config: AppMetricaConfig) {
    AppMetrica.activate(config);
    if(config.sessionsAutoTracking === true || config.sessionsAutoTracking == null){
      sessionTracking();
    };
    if(config.appOpenTrackingEnbled || config.appOpenTrackingEnbled == null){
      appOpenTracking();
    };
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

  reportError(error: Error, _reason: Object) {
    AppMetrica.reportError(error);
  },

  reportEvent(eventName: string, attributes?: Object) {
    AppMetrica.reportEvent(eventName, attributes);
  },

  reportReferralUrl(referralUrl: string) {
    AppMetrica.reportReferralUrl(referralUrl);
  },

  requestStartupParams(listener: StartupParamsCallback) {
    AppMetrica.requestStartupParams(listener);
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

  setStatisticsSending(enabled: boolean) {
    AppMetrica.setStatisticsSending(enabled);
  },

  setUserProfileID(userProfileID?: string) {
    AppMetrica.setUserProfileID(userProfileID);
  },

  reportECommerce(event: ECommerceEvent){
    AppMetrica.reportECommerce(event)
  },
};