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

    public boolean addMember(String name, String level, int productions) throws PricePolicyNotFoundException, IOException {
        PricePolicy pricePolicy = PricePolicy.getFromString(level);
        Member member = new Member(name, pricePolicy, productions);
        memberRegistry.addMember(member);
        return true;
    }

    /**
     * Filtrerar och sorterar medlemmar från fil.
     * @param memberComparator sortera enligt comparator
     * @param pricePolicyFilter filtrera enligt pricePolicy
     * @param searchName filtrerar enligt sökord på namn
     * @return returnerar set med medlemmar
     * @throws IOException Om fil inte kan läsas kastas exception
     */
    public Set<Member> getFilteredAndSortedMembers(Comparator<Member> memberComparator, Class<? extends PricePolicy> pricePolicyFilter, String searchName) throws IOException {
        Set<Member> sortedMemberSet = memberRegistry.getMembers().stream()
                .filter(m -> pricePolicyFilter.isInstance(m.getLevel()))
                .filter(m -> m.getName().toLowerCase().contains(searchName.toLowerCase()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(memberComparator)));
        return sortedMemberSet;
    }

    }
