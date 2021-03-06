package jp.co.equinestudio.parxsample;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FAVORITE_HORSE.
 */
public class FavoriteHorse {

    private Long id;
    private String horse_code;
    private Integer group;
    private Integer reg_timestamp;

    public FavoriteHorse() {
    }

    public FavoriteHorse(Long id) {
        this.id = id;
    }

    public FavoriteHorse(Long id, String horse_code, Integer group, Integer reg_timestamp) {
        this.id = id;
        this.horse_code = horse_code;
        this.group = group;
        this.reg_timestamp = reg_timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorse_code() {
        return horse_code;
    }

    public void setHorse_code(String horse_code) {
        this.horse_code = horse_code;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getReg_timestamp() {
        return reg_timestamp;
    }

    public void setReg_timestamp(Integer reg_timestamp) {
        this.reg_timestamp = reg_timestamp;
    }

}
