# react-native-appmetrica
React Native bridge to the [AppMetrica](https://appmetrica.io) on both iOS and Android.

## Installation

```sh
npm install react-native-appmetrica
```
Replace <your project>/node_modules/react-native-appmetrica with files from this repo

```sh
npx pod-install
```

## Usage

```js
import AppMetrica, { AppMetricaConfig } from 'react-native-appmetrica/src';

// Starts the statistics collection process.
const config: AppMetricaConfig = {
  apiKey: '...KEY...',
  sessionTimeout: 120,
  firstActivationAsUpdate: false,
}
AppMetrica.activate(config);

// Sends a custom event message and additional parameters (optional).
AppMetrica.reportEvent('My event');
AppMetrica.reportEvent('My event', { foo: 'bar' });

// Send a custom error event.
AppMetrica.reportError('My error');
```
# ecommerce

```js
import * as Ecommerce from 'react-native-appmetrica/src/ecommerce';
```

## showScreenEvent
```js
function showScreenEvent(){
  const payload = new Map<string, string>([
    ["configuration", "landscape"],
    ["full_screen", "true"]
  ]);

  // Creating a screen object.
  const screen: Ecommerce.ECommerceScreen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

  const showScreen = Ecommerce.showScreenEvent(screen);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(showScreen);
};
```
## showProductCardEvent
```js
function showProductCardEvent(){
  const payload = new Map<string, string>([
    ["configuration", "landscape"],
    ["full_screen", "true"]
  ]);

  // Creating a screen object.
  const screen: Ecommerce.ECommerceScreen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.
  };

  const amount: Ecommerce.ECommerceAmount = {
    amount: 4.53,
    unit: 'USD'
  };

   // Creating an actualPrice object.
  const actualPrice: Ecommerce.ECommercePrice = {amount: amount,
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

    // Creating an originalPrice object.
  const originalPrice: Ecommerce.ECommercePrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

  // Creating a product object.
  const product: Ecommerce.ECommerceProduct = {
    sku: '779213',
    name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
    actualPrice: actualPrice, // Optional.
    originalPrice: originalPrice, // Optional.
    promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
    payload: payload,    // Optional.
    categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };
  
  const showProductCard = Ecommerce.showProductCardEvent(product, screen);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(showProductCard);
};
```

## showProductDetailsEvent
```js
function showProductDetailsEvent(){
  const payload = new Map<string, string>([
    ["configuration", "landscape"],
    ["full_screen", "true"]
  ]);

  // Creating a screen object.
  const screen: Ecommerce.ECommerceScreen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.
  };

  const amount: Ecommerce.ECommerceAmount = {
    amount: 4.53,
    unit: 'USD'
  };

  // Creating an actualPrice object.
  const actualPrice: Ecommerce.ECommercePrice = {amount: amount,
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

  // Creating an originalPrice object.
  const originalPrice: Ecommerce.ECommercePrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

  // Creating a product object.
  const product: Ecommerce.ECommerceProduct = {
    sku: '779213',
    name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
    actualPrice: actualPrice, // Optional.
    originalPrice: originalPrice, // Optional.
    promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
    payload: payload,    // Optional.
    categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };

  const referrer: Ecommerce.ECommerceReferrer = {
    type: 'button',
    identifier: '76890',
    screen: screen
  };
  
  const showProductDetails = Ecommerce.showProductDetailsEvent(product, referrer);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(showProductDetails);

};
```

## addCartItemEvent, removeCartItemEvent
```js
function addRemoveCartItem(){
  const payload = new Map<string, string>([
    ["configuration", "landscape"],
    ["full_screen", "true"]
  ]);

  // Creating a screen object.
  const screen: Ecommerce.ECommerceScreen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.
  };

  const amount: Ecommerce.ECommerceAmount = {
    amount: 4.53,
    unit: 'USD'
  };

  // Creating an actualPrice object.
  const actualPrice: Ecommerce.ECommercePrice = {amount: amount,
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};
    // Creating an originalPrice object.
  const originalPrice: Ecommerce.ECommercePrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

  // Creating a product object.
  const product: Ecommerce.ECommerceProduct = {
    sku: '779213',
    name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
    actualPrice: actualPrice, // Optional.
    originalPrice: originalPrice, // Optional.
    promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
    payload: payload,    // Optional.
    categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };

  // Creating a referrer object.
  const referrer = {
    type: 'button', // Optional.
    identifier: '76890', // Optional.
    screen: screen // Optional.
  };

  // Creating a cartItem object.
  const ecommerceCartItem = {
    product: product,
    price: actualPrice,
    quantity: 1.0,
    referrer: referrer // Optional.
  };

  const addCartItem = Ecommerce.addCartItemEvent(ecommerceCartItem);

  // Sending an e-commerce event.
  AppMetrica.reportECommerce(addCartItem);

  const removeCartItem = Ecommerce.removeCartItemEvent(ecommerceCartItem);
  // Sending an e-commerce event. 
  AppMetrica.reportECommerce(removeCartItem);
};
```

## beginCheckoutEvent, purchaseEvent
```js
function beginCheckoutPurchaseEvent(){
  const payload = new Map<string, string>([
    ["configuration", "landscape"],
    ["full_screen", "true"]
  ]);

  // Creating a screen object.
  const screen: Ecommerce.ECommerceScreen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.
  };

  const amount: Ecommerce.ECommerceAmount = {
    amount: 4.53,
    unit: 'USD'};

  // Creating an actualPrice object.
  const actualPrice: Ecommerce.ECommercePrice = {amount: amount,
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};

    // Creating an originalPrice object.
  const originalPrice: Ecommerce.ECommercePrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
  ]};


  // Creating a product object.
  const product: Ecommerce.ECommerceProduct = {
  sku: '779213',
  name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
  actualPrice: actualPrice, // Optional.
  originalPrice: originalPrice, // Optional.
  promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
  payload: payload,    // Optional.
  categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };

  // Creating a referrer object.
  const referrer: Ecommerce.ECommerceReferrer = {
    type: 'button', // Optional.
    identifier: '76890', // Optional.
    screen: screen // Optional.
  };
  // Creating a cartItem object.
  const ecommerceCartItem: Ecommerce.ECommerceCartItem = {
    product: product,
    price: actualPrice,
    quantity: 1.0,
    referrer: referrer // Optional.
  };

  const order: Ecommerce.ECommerceOrder = {
    orderId: '88528768',
    products: [ecommerceCartItem],
    payload: undefined,
  };

  const beginCheckout = Ecommerce.beginCheckoutEvent(order);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(beginCheckout);

  const purchase = Ecommerce.purchaseEvent(order);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(purchase);
}
```

## get Identifiers
```js
import {
  StartupParams,
  StartupParamsCallback,
  StartupParamsKeys,
  StartupParamsReason
} from 'react-native-appmetrica/src/index';
...
  const paramsList: Array<StartupParamsKeys> = [ 
    StartupParamsKeys.DEVICE_ID_HASH_KEY,
    StartupParamsKeys.DEVICE_ID_KEY,
    StartupParamsKeys.UUID_KEY
  ];
  const paramsCallback: StartupParamsCallback = (params: StartupParams, reason?: StartupParamsReason) => {
    console.debug(params.deviceIdHash);
    console.debug(params.deviceId);
    console.debug(params.uuid);
  };
  AppMetrica.requestStartupParams(paramsCallback, paramsList);
```

## revenue
  ```js
  import { Revenue } from 'react-native-appmetrica/src/revenue';
  ...
  const revenue: Revenue = {
    price: 500,
    currency: 'USD',
    productID: '12345',
    quantity: 1,
    payload: '{ test: \'test\' }',
    transactionID: 'transactionID',
    receiptData: 'receiptData;',
    signature: 'signature;',
  };
  AppMetrica.reportRevenue(revenue);
```

## adRevenue
```js
  import { AdType, AdRevenue }  from 'react-native-appmetrica/src/revenue';
  ...
  const adRevenue: AdRevenue = {
    price: 100,
    currency: 'RUB',
    payload: new Map<string,string>([
      ["key", "value"],
      ["source", "true"]
    ]),
    adNetwork: 'adNetwork',
    adPlacementID: 'adPlacementId',
    adPlacementName: 'adPlacementName',
    adType: AdType.BANNER,
    adUnitID: '12345',
    adUnitName: 'adUnitName',
    precision: 'precision',
  };
  AppMetrica.reportAdRevenue(adRevenue);

```

## folly/FBString.h not found

change your ios/Podfile:
```pod
  ...
  post_install do |installer|
  ...
    installer.pods_project.targets.each do |target|
      if target.name == 'KSCrash'
        target.build_configurations.each do |config|
          config.build_settings['USE_HEADERMAP'] = 'NO'
        end
      end
    end
  end
```
and use
```sh
npx pod-install
```
