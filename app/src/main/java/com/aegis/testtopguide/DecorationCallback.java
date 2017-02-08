package com.aegis.testtopguide;

/**
 * Created by SFS on 2017/2/8.
 * Description :   定义一个借口方便外界的调用

 */

public interface DecorationCallback {
    String getGroupId(int position);
    String getGroupFirstLine(int position);
}
