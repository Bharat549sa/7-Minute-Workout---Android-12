package com.example.a7minuiteworkout

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

//    @Update
//    suspend fun update(historyentity:HistoryEntity)
//
//    @Delete
//    suspend fun delete(historyentity: HistoryEntity)

    @Query("Select * from 'history-table'")
    fun fetchAllDates():Flow<List<HistoryEntity>>

//ommiting update when it happens, don't have to tell recyeleview to tell it sel fit wil aumatiaclly do it


   // @Query("Select * from 'history-table' where id=:id")

  //  fun fetchDatesById(id:Int): Flow<HistoryEntity>


}