import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

public class App{
private static final String JAVA_AUTHOR = "AutoGen";
private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
private static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
private static final String DB_USER = "scott";
private static final String DB_PASSWORD = "orcl";
private static final String TABLE = "item";

        public static void main(String[] args) {
            AutoGenerator mpg = new AutoGenerator();
            //全局配置
            GlobalConfig gc = new GlobalConfig();
            String  projectPath = "automybatis";
            gc.setOutputDir(projectPath+"/src/main/java");
            gc.setAuthor(JAVA_AUTHOR);
            gc.setOpen(false);
            gc.setIdType(IdType.INPUT);
            gc.setEntityName("%sEntity");
            gc.setBaseResultMap(true);
            mpg.setGlobalConfig(gc);
            //数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl(DB_URL);
            dsc.setDriverName(DB_DRIVER);
            dsc.setUsername(DB_USER);
            dsc.setPassword(DB_PASSWORD);
            mpg.setDataSource(dsc);
            //包配置
            PackageConfig pc = new PackageConfig();
            pc.setParent("cn.ccb.clpm.data.business");
            mpg.setPackageInfo(pc);
            //策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            strategy.setSuperMapperClass("cn.ccb.clpm.common.mapper.MybatisBaseMapper");
            strategy.setInclude(TABLE.split(","));
            mpg.setStrategy(strategy);
            mpg.execute();
        }
}
