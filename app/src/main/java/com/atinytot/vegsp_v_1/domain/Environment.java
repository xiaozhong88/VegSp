package com.atinytot.vegsp_v_1.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Environment {

    private String soil_temp;
    private String soil_humidity;
    private String air_temp;
    private String air_humidity;
    private String hv;
    private String CO2;

    public String getSoil_temp() {
        return soil_temp;
    }

    public void setSoil_temp(String soil_temp) {
        this.soil_temp = soil_temp;
    }

    public String getSoil_humidity() {
        return soil_humidity;
    }

    public void setSoil_humidity(String soil_humidity) {
        this.soil_humidity = soil_humidity;
    }

    public String getAir_temp() {
        return air_temp;
    }

    public void setAir_temp(String air_temp) {
        this.air_temp = air_temp;
    }

    public String getAir_humidity() {
        return air_humidity;
    }

    public void setAir_humidity(String air_humidity) {
        this.air_humidity = air_humidity;
    }

    public String getHv() {
        return hv;
    }

    public void setHv(String hv) {
        this.hv = hv;
    }

    public String getCO2() {
        return CO2;
    }

    public void setCO2(String CO2) {
        this.CO2 = CO2;
    }
}
