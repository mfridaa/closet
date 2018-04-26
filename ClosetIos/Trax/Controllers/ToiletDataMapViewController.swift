//
//  ToiletDataTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 13..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

class ToiletDataTableViewController: ToiletMapViewController
{
    public var container: NSPersistentContainer? =
        (UIApplication.shared.delegate as? AppDelegate)?.persistentContainer
        { didSet {
            print("toiletSet")
            updateUI()
        } }
    
    
    private func updateUI() {
        if let context = container?.viewContext{
            if let toilets = try? Toilet.allToilets(inDatabase: context){
                DispatchQueue.main.async {
                    self.addWayPoints(wayPoints: toilets)
                }
                
            }
        }
    }
    
    
    

}
    

