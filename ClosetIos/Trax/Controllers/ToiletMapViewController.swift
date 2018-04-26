//
//  ViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 02. 28..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation
//CLLocationManagerDelegate
class ToiletMapViewController: UIViewController,MKMapViewDelegate,CLLocationManagerDelegate {
    
    
    struct Constans{
        public static let ShowDetailsSegue = "Show details"
    }
    
    
    
    private struct Constants {
        static let LeftCalloutFrame = CGRect(x: 0, y: 0, width: 59, height: 59)
        static let AnnotationViewReuseIdentifier = "waypoint"
        static let ShowImageSeque = "Show Image"
        static let EditUserWaypoint = "Edit Waypoint"
    }
    
    
    func addWayPoints(wayPoints : [BasicToilet]){
        clearWaypoint()
        mapView?.addAnnotations(wayPoints.map({$0.MKPAnnotation()}))
        mapView?.showAnnotations(wayPoints.map({$0.MKPAnnotation()}), animated: true)
       
    }
    
    
    private func clearWaypoint(){
        mapView?.removeAnnotations(mapView.annotations)
    }
    
    @IBAction func addNewPlace(_ sender: UILongPressGestureRecognizer) {
        if sender.state == .began{
            let coordinate = mapView.convert(sender.location(in: mapView), toCoordinateFrom: mapView)
            if let tabBarviewController = parent as? NavigationTabBarViewController {
                    tabBarviewController.performSegue(withIdentifier: "New Place", sender: coordinate)
  
            }
        }
    }
    
    
    @IBOutlet weak var mapView: MKMapView!
        {
        didSet{
            mapView.mapType = .standard
            mapView.delegate = self
        }
    }
    
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
        if let annotation = view.annotation as? ToiletMKPointAnnotation{
            annotation.refreshStatus()
        }
    }
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        if annotation.title! == "My Location"{
            return nil
        }
        var annotationView: MKAnnotationView! = mapView.dequeueReusableAnnotationView(withIdentifier: Constants.AnnotationViewReuseIdentifier)
        
        if annotationView == nil {
            annotationView = MKAnnotationView(annotation: annotation, reuseIdentifier: Constants.AnnotationViewReuseIdentifier)
            annotationView.canShowCallout = true
            

        } else {
            annotationView.annotation = annotation

        }
        //MAKR: Button for segue to more detail
        let detailButton = UIButton.init(type: UIButtonType.detailDisclosure)
        detailButton.isHidden = false
        annotationView.image = #imageLiteral(resourceName: "CleanToilet")


        annotationView.rightCalloutAccessoryView = detailButton
        annotationView.detailCalloutAccessoryView = nil
        return annotationView
    }
    
    //MARK: -- Informations button pressed
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
        if let button = (control as? UIButton),button.buttonType == UIButtonType.detailDisclosure  {
            mapView.deselectAnnotation(view.annotation, animated: false)
            if let navigationParent =  (parent as? NavigationTabBarViewController) {
                if let annotation = view.annotation as? ToiletMKPointAnnotation{
                    navigationParent.performSegue(withIdentifier: "Show informations", sender: annotation)
                }
                
            }
        }
    }
    
    //Location
    
    let manager = CLLocationManager()

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations[0]

        let myLocation: CLLocationCoordinate2D = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude)
        
        let span:MKCoordinateSpan = MKCoordinateSpanMake(0.01, 0.01)
        let region: MKCoordinateRegion = MKCoordinateRegionMake(myLocation, span)
        
        mapView.setRegion(region, animated: true)
        mapView.showsUserLocation = true
    }
    
   
    override func viewDidLoad() {
        super.viewDidLoad()
    
        manager.delegate = self
        manager.requestAlwaysAuthorization()
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.startUpdatingLocation()
        
    }

    
    
}
extension MKPointAnnotation{
    
}



