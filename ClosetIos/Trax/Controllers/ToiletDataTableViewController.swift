//
//  ToiletDataTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData

class ToiletDataTableViewController: ToiletTableViewViewController
{
    var container: NSPersistentContainer? = (UIApplication.shared.delegate as? AppDelegate)?.persistentContainer

    override func addWayPoints(wayPoints: [BasicToilet]) {
        super.addWayPoints(wayPoints: wayPoints)
//        updateDatabase(with: wayPoints)
    }
    
    private func updateDatabase(with toilets :[BasicToilet]){
        container?.performBackgroundTask{ contex in
            for toiletInfo in toilets{
                print("asd")
                _ = try? Toilet.findOrCreateToilet(matching: toiletInfo, in: contex)
            }
            try? contex.save()
        }
        printDatabaseStatistics()
    }
    
    private func printDatabaseStatistics(){
        if let context = container?.viewContext{
//            let request: NSFetchRequest<Toilet> = Toilet.fetchRequest()
//            if let count = (try? context.fetch(Toilet.fetchRequest()))?.count {
//                print("\(count) toilets")
//            }
            if let count = try? context.count(for: Toilet.fetchRequest()) {
                print("\(count) toilets")
            }
        }
    }

}
