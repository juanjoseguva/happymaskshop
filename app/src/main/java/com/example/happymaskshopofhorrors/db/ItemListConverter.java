package com.example.happymaskshopofhorrors.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;
/*
This used to be a much cooler type converter from string to List<Items>, but now is kinda vestigial,
in the sense that this can be done in situ
 */
public class ItemListConverter {

    private static HappyMaskDAO mHappyMaskDAO;

    @TypeConverter
    public static String convertItemListToString(List<Integer> list){
        StringBuilder sb = new StringBuilder();
        for (Integer itemId : list) {
            sb.append(itemId);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
    @TypeConverter
    public static List<Integer> convertStringToItemList(String itemIds){
        List<Integer> items = new ArrayList<>();
        String[] ItemIds = itemIds.split(",");
        String[] empty = new String[]{""};
        if(ItemIds[0].equals("")){
            return items;
        } else if(ItemIds.length > 0) {
            for (String itemId : ItemIds) {
                items.add(Integer.parseInt(itemId.trim()));
            }
        }
        return items;
    }

}
