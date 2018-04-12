//
//  BasicToilet.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 03. 02..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

struct BasicToilet:Decodable,Encodable {
    let name: String
    let latitudeAndLongitude: MapCoordinate
    let rating: Float
    let status: String
    let ratings:[Float]
    
    public func MKPAnnotation()->MKPointAnnotation{
        let annotation = MKPointAnnotation()
        annotation.coordinate = CLLocationCoordinate2D(latitude: CLLocationDegrees(exactly: latitudeAndLongitude.latitude)!, longitude: CLLocationDegrees(exactly: latitudeAndLongitude.longitude)!)
        annotation.title = name
        annotation.subtitle = status
        return annotation
    }
    
    public func PostForm()->NameAndLatitudeAndLongitude{
        return NameAndLatitudeAndLongitude(name: name, latitude: latitudeAndLongitude.latitude, longitude: latitudeAndLongitude.longitude)
    }
    
}
struct MapCoordinate:Decodable,Encodable {
    let latitude:Float
    let longitude:Float
}

