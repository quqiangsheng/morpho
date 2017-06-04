package com.max256.morpho.common.util.ip2region;

/**
 * database configuration class
 * 
 * @author chenxin<chenxin619315@gmail.com>
*/
public class DbConfig 
{
    /**
     * total header data block size
    */
    private int totalHeaderSize;
    
    /**
     * max index data block size
     * u should always choice the fastest read block size 
    */
    private int indexBlockSize;
    
    /**
     * construct method
     * 
     * @param    totalHeaderSize
     * @param    dataBlockSize
     * @throws DbMakerConfigException 
    */
    public DbConfig( int totalHeaderSize ) throws DbMakerConfigException
    {
        if ( (totalHeaderSize % 8) != 0 ) {
            throw new DbMakerConfigException("totalHeaderSize must be times of 8");
        }
        
        this.totalHeaderSize = totalHeaderSize;
        this.indexBlockSize = 4096; //4 * 1024
    }
    
    public DbConfig() throws DbMakerConfigException
    {
        this(8192);
    }

    public int getTotalHeaderSize()
    {
        return totalHeaderSize;
    }

    public DbConfig setTotalHeaderSize(int totalHeaderSize)
    {
        this.totalHeaderSize = totalHeaderSize;
        return this;
    }

    public int getIndexBlockSize()
    {
        return indexBlockSize;
    }

    public DbConfig setIndexBlockSize(int dataBlockSize)
    {
        this.indexBlockSize = dataBlockSize;
        return this;
    }
}
