package service;

import dao.MemberRegistry;
import entity.member.Member;
import entity.member.pricepolicy.PricePolicy;
import exceptions.PricePolicyNotFoundException;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MemberService {
    private MemberRegistry memberRegistry = new MemberRegistry("members.json");

    public void addMember(String name, String level, int productions) throws PricePolicyNotFoundException, IOException {
        PricePolicy pricePolicy = PricePolicy.getFromString(level);
        Member member = new Member(name, pricePolicy, productions);
        memberRegistry.addMember(member);
    }

    

    }
