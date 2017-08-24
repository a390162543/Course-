package cn.zhsite.dao.impl;

import cn.zhsite.dao.QuitDAO;
import cn.zhsite.model.Quit;
import org.springframework.stereotype.Repository;


@Repository
public class QuitDAOImpl extends BaseDAOImpl<Quit> implements QuitDAO {

    @Override
    protected Class getModelClass() {
        return Quit.class;
    }
}
