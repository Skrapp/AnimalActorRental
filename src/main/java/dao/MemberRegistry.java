package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.member.Member;
import entity.member.pricepolicy.Regular;
import exceptions.MemberNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberRegistry {
    private File memberFile;
    private ObjectMapper mapper = new ObjectMapper();

    public MemberRegistry(String fileName) {
        this.memberFile = new File(fileName);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void removeMemberByID(List<String> ids) throws IOException, MemberNotFoundException {
        List<Member> members = getMembers();
        //Om det inte finns någon medlem med givet id så kan det inte tas bort, därav är det troligtvis fel någonstans
        for(String id : ids) {
            if (!members.removeIf(m -> m.getId().equals(id))) {
                throw new MemberNotFoundException("Medlem med id \"" + id + "\" finns inte i medlemsregister.");
            }
        }
        reloadFile(members);
    }

    public boolean addMember(Member member) throws IOException{
        List<Member> members = getMembers();
        members.add(member);
        reloadFile(members);
        return true;
    }

    public List<Member> getMembers() throws IOException{
        if (!memberFile.exists() || memberFile.length() == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(mapper.readValue(memberFile, Member[].class)));
    }

    public void reloadFile(List<Member> members) throws IOException{
        mapper.writeValue(memberFile, members);
    }

    public File getMemberFile() {
        return memberFile;
    }

    public void setMemberFile(File memberFile) {
        this.memberFile = memberFile;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
