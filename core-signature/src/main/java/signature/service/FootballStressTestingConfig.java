package signature.service;

import com.ximalaya.football.client.spring.annotation.FootballConfig;
import com.ximalaya.football.client.spring.annotation.FootballField;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FootballConfig("stressTesting")
public class FootballStressTestingConfig{

    @FootballField(name = "ignoreSigAppKeys",defaultValue = "[]")
    private List<String> ignoreSigAppKeys;

    public List<String> getIgnoreSigAppKeys(){
        return ignoreSigAppKeys;
    }

    public void setIgnoreSigAppKeys(List<String> ignoreSigAppKeys){
        this.ignoreSigAppKeys = ignoreSigAppKeys;
    }

    public boolean isStressTestingAppKey(String appKey){
        Set<String> appKeys = new HashSet<>(ignoreSigAppKeys);
        return appKeys.contains(appKey);
    }
}
