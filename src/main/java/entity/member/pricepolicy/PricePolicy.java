package entity.member.pricepolicy;

import exceptions.PricePolicyNotFoundException;

public interface PricePolicy {
    double applyDiscount(double amount);
    double getFee();

    //TODO ska denna vara i MemberService?
    static PricePolicy getFromString(String name) throws PricePolicyNotFoundException {
        switch(name.toLowerCase()){
            case "golden":{
                return new Golden();
            }
            case "regular": {
                return new Regular();
            }
            case "student": {
                return new Student();
            }
            default: {
                throw new PricePolicyNotFoundException(name + " är inte ett giltigt pricePolicy");
            }
        }
    }
}
