package service;

import dao.MemberRegistry;
import entity.member.Member;
import entity.member.pricepolicy.PricePolicy;
import exceptions.MemberNotFoundException;
import exceptions.PricePolicyNotFoundException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemberService {
    private MemberRegistry memberRegistry = new MemberRegistry("members.json");

    public boolean addMember(String name, String level, int productions) throws PricePolicyNotFoundException, IOException {
        PricePolicy pricePolicy = PricePolicy.getFromString(level);
        Member member = new Member(name, pricePolicy, productions);
        memberRegistry.addMember(member);
        return true;
    }

    public boolean addMember(Member member) throws IOException {
        memberRegistry.addMember(member);
        return true;
    }

    public void removeMember(Member memberToRemove) throws IOException, MemberNotFoundException {
        List<String> membersId = new ArrayList<>();
        membersId.add(memberToRemove.getId());
        memberRegistry.removeMemberByID(membersId);
    }

    public void removeMembers(List<Member> members) throws IOException, MemberNotFoundException {
        List<String> membersId =
                members.stream()
                        .map(Member::getId)
                        .collect(Collectors.toList());
        memberRegistry.removeMemberByID(membersId);
    }

    public void updateMember(Member member) throws IOException, MemberNotFoundException {
        removeMember(member);
        addMember(member);
    }

    public Member getMemberByID(String id) throws IOException, MemberNotFoundException {
        List<Member> allMembers = getAllMembers();
        for(Member member : allMembers){
            if(member.getId().equals(id)){
                return member;
            }
        }
        throw new MemberNotFoundException("Kunde inte hitta id \"" + id + "\" i medlemsregister.");
    }

    public List<Member> getAllMembers() throws IOException {
        return memberRegistry.getMembers();
    }

    /**
     * Filtrerar och sorterar medlemmar från fil.
     *
     * @param searchWord         filtrerar enligt sökord på namn eller id
     * @param pricePolicyClasses filtrera enligt pricePolicy. För att inkludera alla PricePolicy använd pricePolicy.class
     * @return returnerar set med medlemmar
     * @throws IOException Om fil inte kan läsas kastas exception
     */
    public Set<Member> getFilteredMembers(String searchWord, List<Class<? extends PricePolicy>> pricePolicyClasses,
                                          int minProductions, int maxProductions)
            throws IOException {
        Set<Member> membersSinglePricePolicy;
        Set<Member> members = new HashSet<>();
        for(Class<? extends PricePolicy> pricePolicyClass : pricePolicyClasses){
        membersSinglePricePolicy = memberRegistry.getMembers().stream()
                .filter(m -> (pricePolicyClass.isInstance(m.getLevel())
                    && (m.getName().toLowerCase().contains(searchWord.toLowerCase())
                        || m.getId().toLowerCase().contains(searchWord.toLowerCase()))
                    && m.getProductions() >= minProductions && m.getProductions() <= maxProductions))
                .collect(Collectors.toCollection(HashSet::new));
        members.addAll(membersSinglePricePolicy);
        }
        return members;
    }

    public Set<Member> getFilteredMembers(String searchWord, List<Class<? extends PricePolicy>> pricePolicyClasses)
            throws IOException {
        Set<Member> membersSinglePricePolicy;
        Set<Member> members = new HashSet<>();
        for(Class<? extends PricePolicy> pricePolicyClass : pricePolicyClasses){
            membersSinglePricePolicy = memberRegistry.getMembers().stream()
                    .filter(m -> (pricePolicyClass.isInstance(m.getLevel())
                            && (m.getName().toLowerCase().contains(searchWord.toLowerCase())
                            || m.getId().toLowerCase().contains(searchWord.toLowerCase()))))
                    .collect(Collectors.toCollection(HashSet::new));
            members.addAll(membersSinglePricePolicy);
        }
        return members;
    }

    }
