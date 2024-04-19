# react-native-appmetrica
React Native bridge to the [AppMetrica](https://appmetrica.yandex.com/) on both iOS and Android.

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
import AppMetrica from 'react-native-appmetrica';

// Starts the statistics collection process.
AppMetrica.activateWithConfig({
  apiKey: '...KEY...',
  sessionTimeout: 120,
  firstActivationAsUpdate: false,
});

// Sends a custom event message and additional parameters (optional).
AppMetrica.reportEvent('My event');
AppMetrica.reportEvent('My event', { foo: 'bar' });

// Send a custom error event.
AppMetrica.reportError('My error');
```
ecommerce

```
import { 
  showProductCardEvent,
  showScreenEvent,
  showProductDetailsEvent,
  addCartItemEvent,
  removeCartItemEvent,
  beginCheckoutEvent,
  purchaseEvent
} from 'react-native-appmetrica/src/ecommerce';

// showScreenEvent
function ecomScreeen(){

  const payload = {
    'configuration': 'landscape',
    'full_screen': 'true'
  };

  // Creating a screen object.
  const screen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

const showScreen = showScreenEvent(screen);
// Sending an e-commerce event.
AppMetrica.reportECommerce(showScreen);

};

//showProductCardEvent
function ecomCard(){

  const payload = {
    'configuration': 'landscape',
    'full_screen': 'true'
  };

  // Creating a screen object.
  const screen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

  const amount = {
    amount: 4.53,
   unit: 'USD'};
  // Creating an actualPrice object.
  const actualPrice = {amount: amount,
      internalComponents: [  // Optional.
      {amount: 30570000,
      unit: 'wood'},
      {amount: 26.89,
      unit: 'iron'},
      {amount: 5.1,
      unit: 'gold'}
  ]
};
    // Creating an originalPrice object.
  const originalPrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
]
};


  // Creating a product object.
  const product = {
  sku: '779213',
  name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
  actualPrice: actualPrice, // Optional.
  originalPrice: originalPrice, // Optional.
  promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
  payload: payload,    // Optional.
  categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };

  const showProductCard = showProductCardEvent(product, screen);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(showProductCard);

};

//showProductDetailsEvent
function ecomDetails(){

  const payload = {
    'configuration': 'landscape',
    'full_screen': 'true'
  };

  // Creating a screen object.
  const screen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

  const amount = {
    amount: 4.53,
   unit: 'USD'};
  // Creating an actualPrice object.
  const actualPrice = {amount: amount,
      internalComponents: [  // Optional.
      {amount: 30570000,
      unit: 'wood'},
      {amount: 26.89,
      unit: 'iron'},
      {amount: 5.1,
      unit: 'gold'}
  ]};
    // Creating an originalPrice object.
  const originalPrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
]};


  // Creating a product object.
  const product = {
  sku: '779213',
  name: 'Продукт творожный «Даниссимо» 5.9%, 130 г.',  // Optional.
  actualPrice: actualPrice, // Optional.
  originalPrice: originalPrice, // Optional.
  promocodes: ['BT79IYX', 'UT5412EP'],  // Optional.
  payload: payload,    // Optional.
  categoriesPath: ['Продукты', 'Молочные продукты', 'Йогурты']  // Optional.
  };

  const referrer = {
    type: 'button',
    identifier: '76890',
    screen: screen
  };

  const showProductDetails = showProductDetailsEvent(product, referrer);
  // Sending an e-commerce event.
  AppMetrica.reportECommerce(showProductDetails);

};

//addCartItemEvent, removeCartItemEvent
function addRemoveCartItem(){
  const payload = {
    'configuration': 'landscape',
    'full_screen': 'true'
  };

  // Creating a screen object.
  const screen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

  const amount = {
    amount: 4.53,
   unit: 'USD'};
  // Creating an actualPrice object.
  const actualPrice = {amount: amount,
      internalComponents: [  // Optional.
      {amount: 30570000,
      unit: 'wood'},
      {amount: 26.89,
      unit: 'iron'},
      {amount: 5.1,
      unit: 'gold'}
  ]};
    // Creating an originalPrice object.
  const originalPrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
]};


  // Creating a product object.
  const product = {
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
  const addCartItem = addCartItemEvent(ecommerceCartItem);

    // Sending an e-commerce event.
    AppMetrica.reportECommerce(addCartItem);

    const removeCartItem = removeCartItemEvent(ecommerceCartItem);
// Sending an e-commerce event.
    AppMetrica.reportECommerce(removeCartItem);
};


//beginCheckoutEvent, purchaseEvent
function beginCheckoutPurchaseEvent(){

  const payload = {
    'configuration': 'landscape',
    'full_screen': 'true'
  };

  // Creating a screen object.
  const screen = {
    name: 'ProductCardActivity',               // Optional.
    searchQuery: 'даниссимо кленовый сироп',   // Optional.
    payload: payload,                          // Optional.
    categoriesPath: ['Акции','Красная цена']   // Optional.

  };

  const amount = {
    amount: 4.53,
    unit: 'USD'};

  // Creating an actualPrice object.
  const actualPrice = {amount: amount,
      internalComponents: [  // Optional.
      {amount: 30570000,
      unit: 'wood'},
      {amount: 26.89,
      unit: 'iron'},
      {amount: 5.1,
      unit: 'gold'}
  ]};
    // Creating an originalPrice object.
  const originalPrice = {amount: {amount: 5.78, unit: 'USD'},
    internalComponents: [  // Optional.
    {amount: 30570000,
    unit: 'wood'},
    {amount: 26.89,
    unit: 'iron'},
    {amount: 5.1,
    unit: 'gold'}
]};


  // Creating a product object.
  const product = {
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

  const order = {
    orderId: '88528768',
    products: [ecommerceCartItem],
    payload: payload,
  };

  const beginCheckout = beginCheckoutEvent(order);
  //   // Sending an e-commerce event.
    AppMetrica.reportECommerce(beginCheckout);
    const purchase = purchaseEvent(order);
// Sending an e-commerce event.
    AppMetrica.reportECommerce(purchase);
}
```

get Identifiers
```
  const paramsList = [AppMetrica.DEVICE_ID_HASH_KEY, AppMetrica.DEVICE_ID_KEY, AppMetrica.UUID_KEY];
  const paramsCallback = (params, reason) => {
    console.debug(params.deviceIdHash);
    console.debug(params.deviceId);
    console.debug(params.uuid);
  };
  AppMetrica.requestStartupParams(paramsCallback, paramsList);
```

revenue
  ```
  const revenue = {
    price: 500,
    currency: 'USD',
    productID: '12345', // optional
    quantity: 1, // optional
    payload: { param: '1234' }, // optional. additional info
    transactionID: 'transactionID' //optional. ios revenue transaction id,
    receiptData: 'receiptData;' // optional. android purchase orijinalJson || ios receiptData,
    signature: 'signature;', // optional. android purchase Signature
  };
  AppMetrica.reportRevenue(revenue);
```

adRevenue
```
  import { AdType }  from 'react-native-appmetrica/src/revenue.ts';
  ...
  const adRevenue = {
    price: 100,
    currency: 'RUB',
    payload: { param: '1234' }, // optional. additional info
    adNetwork: 'adNetwork', // optional
    adPlacementId: 'adPlacementId', // optional
    adPlacementName: 'adPlacementName', // optional
    adType: AdType.BANNER, // optional
    adUnitId: '12345', // optional
    adUnitName: 'adUnitName', // optional
    precision: 'precision', // optional
  };
  AppMetrica.reportAdRevenue(adRevenue);

```

folly/FBString.h not found

change your ios/Podfile: 
```
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

## License

MIT
