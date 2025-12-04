package entity.member;

import java.util.Comparator;

public class MemberIDComparator implements Comparator<Member> {
    @Override
    public int compare(Member m1, Member m2) {
        return m1.getId().compareTo(m2.getId());
    }
}
