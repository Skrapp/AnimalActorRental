package entity.member.pricepolicy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import exceptions.PricePolicyNotFoundException;

/**
 *
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Regular.class, name = "Regular"),
    @JsonSubTypes.Type(value = Golden.class, name = "Golden"),
    @JsonSubTypes.Type(value = Student.class, name = "Student")
})

public interface PricePolicy {
    double applyDiscount(double amount);
    double payFee();

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
