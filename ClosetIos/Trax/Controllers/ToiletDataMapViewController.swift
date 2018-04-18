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
        { didSet { updateUI() } }
    
    
    private func updateUI() {
        if let context = container?.viewContext{
            if let toilets = try? Toilet.allToilets(inDatabase: context){
                DispatchQueue.main.async {
                    self.addWayPoints(wayPoints: toilets)
                }
                
            }
        }
    }
    
//    override func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
//        if let button = (control as? UIButton),button.buttonType == UIButtonType.detailDisclosure  {
//            mapView.deselectAnnotation(view.annotation, animated: false)
//            if let navigationParent =  (parent as? NavigationTabBarViewController) {
//                navigationParent.performSegue(withIdentifier: "Show informations", sender: view.annotation)
//            }
//        }
//    }

    
    
    override func addWayPoints(wayPoints: [BasicToilet]) {

        super.addWayPoints(wayPoints: wayPoints)
        updateDatabase(with: wayPoints)
    }
    
    private func updateDatabase(with toilets :[BasicToilet]){
        container?.performBackgroundTask{ contex in
            for toiletInfo in toilets{
                _ = try? Toilet.findOrCreateToilet(matching: toiletInfo, in: contex)
            }
            try? contex.save()
        }

    }
}
    

