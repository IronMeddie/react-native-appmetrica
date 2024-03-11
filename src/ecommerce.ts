type ECommerceScreen = {
    name: string,
    searchQuery?: string,
    payload?: Map<string, string>,
    categoriesPath?: Array<string>
}

type ECommerceAmount = {
    amount: number,
    unit: string
}

type ECommercePrice = {
    amount: ECommerceAmount,
    internalComponents?: Array<ECommerceAmount>
}

type ECommerceProduct = {
    sku: string,
    name?: string,
    actualPrice?: ECommercePrice,
    originalPrice?: ECommercePrice,
    promocodes?: Array<string>,
    categoriesPath?: Array<string>,
    payload?: Map<string, string>
}

type ECommerceReferrer = {
    type?: string,
    identifier?: string,
    screen?: ECommerceScreen
}

type ECommerceCartItem = {
    product: ECommerceProduct,
    price: ECommercePrice,
    quantity: number,
    referrer?: ECommerceReferrer
}

type ECommerceOrder = {
    orderId: string,
    products: Array<ECommerceCartItem>,
    payload?: Map<string, string>
}


export interface ECommerceEvent {
    ecommerceEvent: string;
    ecommerceScreen?: ECommerceScreen;
    product?: ECommerceProduct;
    referrer?: ECommerceReferrer;
    cartItem?: ECommerceCartItem;
    order?: ECommerceOrder;
}

export class ECommerceClass {
    public showScreenEvent(screen: ECommerceScreen): ECommerceEvent {
        return {
            ecommerceEvent: 'showSceenEvent',
            ecommerceScreen: screen
        }
    };
    public showProductCardEvent(product: ECommerceProduct, screen: ECommerceScreen): ECommerceEvent {
        return {
            ecommerceEvent: 'showProductCardEvent',
            ecommerceScreen: screen,
            product: product
        }
    };
    public showProductDetailsEvent(product: ECommerceProduct, referrer: ECommerceReferrer): ECommerceEvent {
        return {
            ecommerceEvent: 'showProductDetailsEvent',
            product: product,
            referrer: referrer
        }
    };
    public addCartItemEvent(item: ECommerceCartItem): ECommerceEvent {
        return {
            ecommerceEvent: 'addCartItemEvent',
            cartItem: item
        }
    };
    public removeCartItemEvent(item: ECommerceCartItem): ECommerceEvent {
        return {
            ecommerceEvent: 'removeCartItemEvent',
            cartItem: item
        }
    };
    public beginCheckoutEvent(order: ECommerceOrder): ECommerceEvent {
        return {
            ecommerceEvent: 'beginCheckoutEvent',
            order: order
        }
    };
    public purchaseEvent(order: ECommerceOrder): ECommerceEvent {
        return {
            ecommerceEvent: 'purchaseEvent',
            order: order
        }
    };
}

export default ECommerceClass

export function showScreenEvent(screen: ECommerceScreen): ECommerceEvent {
    return {
        ecommerceEvent: 'showSceenEvent',
        ecommerceScreen: screen
    }
};

export function showProductCardEvent(product: ECommerceProduct, screen: ECommerceScreen): ECommerceEvent {
    return {
        ecommerceEvent: 'showProductCardEvent',
        ecommerceScreen: screen,
        product: product
    }
};

export function showProductDetailsEvent(product: ECommerceProduct, referrer: ECommerceReferrer): ECommerceEvent {
    return {
        ecommerceEvent: 'showProductDetailsEvent',
        product: product,
        referrer: referrer
    }
};

export function addCartItemEvent(item: ECommerceCartItem): ECommerceEvent {
    return {
        ecommerceEvent: 'addCartItemEvent',
        cartItem: item
    }
};

export function removeCartItemEvent(item: ECommerceCartItem): ECommerceEvent {
    return {
        ecommerceEvent: 'removeCartItemEvent',
        cartItem: item
    }
};

export function beginCheckoutEvent(order: ECommerceOrder): ECommerceEvent {
    return {
        ecommerceEvent: 'beginCheckoutEvent',
        order: order
    }
};

export function purchaseEvent(order: ECommerceOrder): ECommerceEvent {
    return {
        ecommerceEvent: 'purchaseEvent',
        order: order
    }
};