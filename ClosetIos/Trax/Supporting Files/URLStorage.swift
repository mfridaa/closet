//
//  UrlStorage.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 07..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

struct URLStorage{
    private static let ServerURL = "http://80.211.203.158:8080/"
    
    public static let getToilets:String = URLStorage.ServerURL + "toilet/all"
    public static let addToilet:String = URLStorage.ServerURL + "toilet/add"
    public static func getRating(forToiletId id :Int) -> String{
        return URLStorage.ServerURL + "toilet/rating/\(id)"
    }
    public static func getStatus(forToiletId id :Int) -> String{
        return URLStorage.ServerURL + "toilet/status/\(id)"
    }
    public static func getToilet(forToiletId id : Int) ->String{
        return URLStorage.ServerURL + "toilet/get/\(id)"
    }


    
}
