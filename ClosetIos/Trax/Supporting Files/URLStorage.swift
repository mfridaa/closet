//
//  UrlStorage.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 07..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

struct URLStorage{
    private static let ServerURL = "http://188.6.28.33:80/"
    
    public static let getToilets:String = URLStorage.ServerURL + "toilet/all"
    public static let addToilet:String = URLStorage.ServerURL + "toilet/add"


    
}
