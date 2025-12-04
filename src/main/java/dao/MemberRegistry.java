package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.member.Member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberRegistry {
    private File memberFile;
    private ObjectMapper mapper = new ObjectMapper();

    public MemberRegistry() {
    }

    public MemberRegistry(String fileName) {
        this.memberFile = new File(fileName);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public boolean addMember(Member member) throws IOException{
        List<Member> members = null;
        try {
            members = getMembers();
        } catch (IOException e) {
            //Om det inte går att hämta för att filen inte finns ska en ny arrayList skapas
            members = new ArrayList<>();
        }
        members.add(member);
        reloadFile(members);
        return true;
    }

    public List<Member> getMembers()throws IOException{
        return new ArrayList<>(Arrays.asList(mapper.readValue(memberFile,Member[].class)));
    }

    public void reloadFile(List<Member> members) throws IOException{
        mapper.writeValue(memberFile, members);
        System.out.println("Sparad");
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
