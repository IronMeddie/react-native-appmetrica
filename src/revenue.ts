export type Revenue = {
    price: number;
    currency: string;
    productID?: string;
    quantity?: number;
    payload?: string;
    transactionID?: string;
    receiptData?: string;
    signature?: string;
};

export type AdRevenue = {
    price: number;
    currency: string;
    payload?: Map<string, string>;
    adNetwork?: string;
    adPlacementID?: string;
    adPlacementName?: string;
    adType?: AdType;
    adUnitID?: string;
    adUnitName?: string;
    precision?: string;
};

export enum AdType {
    NATIVE = "native",
    BANNER = "banner",
    MREC =  "mrec",
    INTERSTITIAL = "interstitial",
    REWARDED = "rewarded",
    OTHER = "other"
}
