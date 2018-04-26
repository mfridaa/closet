//
//  BasicRating.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 15..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//


struct BasicRating : Encodable,Decodable{
    var id:Int
    var rater:String
    var ratingNum:Int
    var description:String
    
}
