//
//  OpeningHour.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 22..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//


struct OpeningHour : Encodable,Decodable {
    var day:String
    var openingHour:String
    var closingHour:String
}
