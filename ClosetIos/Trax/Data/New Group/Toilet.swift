//
//  Toilet.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

class Toilet: NSManagedObject
{
    class func findOrCreateToilet(matching toilet: BasicToilet, in context : NSManagedObjectContext) throws -> Toilet
    {
        let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
        // TODO: --add longitude to search
        request.predicate = NSPredicate(format: "id = %d", toilet.id)
        do{
            let matches = try context.fetch(request)
            if matches.count > 0{
//                assert(matches.count > 1, "Toilet.findOrCreateToilet -- inconsistencs")
                return matches[0]
            }
        } catch {
            print("error")
            throw error
        }
       
        let newToilet = Toilet(context: context)
        newToilet.latitude = toilet.location.latitude
        newToilet.longitude = toilet.location.longitude
        newToilet.name = toilet.name
        newToilet.id = Int16(toilet.id)
//        newToilet.creator = toilet.creator
        newToilet.status = toilet.status
        return newToilet
    }
    
    class func findToilet(longitude: Float, latitude: Float, in context : NSManagedObjectContext) throws -> Toilet? {
        let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
        request.predicate = NSPredicate(format: "latitude = %f AND longitude = %f", latitude,longitude)
        do{
            let matches = try context.fetch(request)
            if matches.count > 0{
//                assert(matches.count > 1, "Toilet.findOrCreateToilet -- inconsistencs")
                return matches[0]
            }
        } catch {
            print("errrror")
            throw error
        }
        return nil
    }
    
    
    class func findOrCreateToilet(matching toilet: Toilet, in context : NSManagedObjectContext) throws -> MKAnnotation?
    {
        let annotation = MKPointAnnotation()
        annotation.coordinate = CLLocationCoordinate2D(latitude: CLLocationDegrees(exactly: toilet.latitude)!, longitude: CLLocationDegrees(exactly: toilet.longitude)!)
        annotation.title = toilet.name
        annotation.subtitle = toilet.status
        do{
            _ = try findOrCreateToilet(matching: toilet, in: context)
        } catch {
            throw error
        }

        return annotation
        
        
    }
    
    class func findToilet(withId id : Int16, in context : NSManagedObjectContext) throws -> Toilet? {
        let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
        request.predicate = NSPredicate(format: "id = %f", id)
        do{
            let matches = try context.fetch(request)
            if matches.count > 0{
                //                assert(matches.count > 1, "Toilet.findOrCreateToilet -- inconsistencs")
                return matches[0]
            }
        } catch {
            print("errrror")
            throw error
        }
        return nil
    }
    
    class func numberOfToilets(InDatabase context : NSManagedObjectContext) throws -> Int{
        return try context.fetch(Toilet.fetchRequest()).count
        
    }
    
    class func allToilets(inDatabase context : NSManagedObjectContext) throws -> [BasicToilet]{
        let request : NSFetchRequest<Toilet> = Toilet.fetchRequest()
        let toilets = try context.fetch(request)
        var resultToilets = [BasicToilet]()
        for toilet in toilets{
            let newToilet = BasicToilet(id:toilet.id,name: toilet.name ?? "", location: MapCoordinate(latitude: toilet.latitude, longitude: toilet.longitude), rating: toilet.rating, status: toilet.status ?? "unknown")
            resultToilets.append(newToilet)
        }
        print("asd")
        return resultToilets
    }
}
