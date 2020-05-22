package priv.patrick.springBucks.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coffee implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Date createTime;
    private Date updateTime;
}
