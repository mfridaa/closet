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
class ViewController: UIViewController,MKMapViewDelegate,CLLocationManagerDelegate {
    
    // MARK: toiletBarItem
    @IBOutlet weak var toiletBarItem: UITabBarItem!
    // MARK: spinningBar
    @IBOutlet weak var dataComing: UIActivityIndicatorView!
    
    
    private struct Constants {
        static let LeftCalloutFrame = CGRect(x: 0, y: 0, width: 59, height: 59)
        static let AnnotationViewReuseIdentifier = "waypoint"
        static let ShowImageSeque = "Show Image"
        static let EditUserWaypoint = "Edit Waypoint"
    }
    
    private var toilets:[BasicToilet]?{
        didSet{
            if let data = toilets{
               
                addWayPoints(wayPoints: data)
            }
            
        }
    }
    
    private func clearWaypoint(){
        mapView?.removeAnnotation(mapView.annotations as! MKAnnotation)
    }
    
    @IBAction func addNewPlace(_ sender: UILongPressGestureRecognizer) {
        if sender.state == .began{
            let coordinate = mapView.convert(sender.location(in: mapView), toCoordinateFrom: mapView)
            let ratings = [Float]()
            let toilet = BasicToilet(name: "newToilet", latitudeAndLongitude: MapCoordinate(latitude: Float(coordinate.latitude), longitude: Float(coordinate.longitude)), rating: 0, status: "", ratings: ratings)
            mapView.addAnnotation(toilet.MKPAnnotation())
//            postNewToilet(of: toilet)
            
        }
    }
    private func addWayPoints(wayPoints : [BasicToilet]){

        mapView?.addAnnotations(wayPoints.map({$0.MKPAnnotation()}))
        mapView?.showAnnotations(wayPoints.map({$0.MKPAnnotation()}), animated: true)
    }
    
    @IBOutlet weak var mapView: MKMapView!
        {
        didSet{
            mapView.mapType = .standard
            mapView.delegate = self
        }
    }
    
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
//        if annotation.title! == "My Location"{
//
//            return nil
//        }
        
        var annotationView: MKAnnotationView! = mapView.dequeueReusableAnnotationView(withIdentifier: Constants.AnnotationViewReuseIdentifier)
        print("asd")
        if annotation is MKUserLocation {
            let pin = mapView.view(for: annotation) as? MKPinAnnotationView ?? MKPinAnnotationView(annotation: annotation, reuseIdentifier: nil)
            pin.pinTintColor = UIColor.purple
            pin.image! = #imageLiteral(resourceName: "CleanToilet")
            return pin
            
        }
        
        if annotationView == nil {
            annotationView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: Constants.AnnotationViewReuseIdentifier)
            annotationView.canShowCallout = true
            
        } else {
            annotationView.annotation = annotation
            
        }
        let detailButton = UIButton.init()
        detailButton.setImage(#imageLiteral(resourceName: "CleanToilet"), for: .focused)
        detailButton.isHidden = false
        annotationView.image! = #imageLiteral(resourceName: "CleanToilet")
        annotationView.leftCalloutAccessoryView = detailButton
        annotationView.detailCalloutAccessoryView = nil
        return annotationView
    }
    
    let manager = CLLocationManager()

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations[0]

        let span:MKCoordinateSpan = MKCoordinateSpanMake(0.01, 0.01)

        let myLocation: CLLocationCoordinate2D = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude)
        let region: MKCoordinateRegion = MKCoordinateRegionMake(myLocation, span)
            
        mapView.setRegion(region, animated: true)
        


    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        manager.delegate = self
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.requestAlwaysAuthorization()
        manager.startUpdatingLocation()
        // Do any additional setup after loading the view, typically from a nib.
        toilets = [BasicToilet]()
        toilets!.append(BasicToilet(name: "Corvinus", latitudeAndLongitude: MapCoordinate(latitude: 47.485715, longitude: 19.058456), rating: 0, status: "Opened", ratings: []))
        toilets!.append(BasicToilet(name: "ELTE", latitudeAndLongitude: MapCoordinate(latitude: 47.474606, longitude: 19.062104), rating: 0, status: "Opened", ratings: []))
        toilets!.append(BasicToilet(name: "WestEnd", latitudeAndLongitude: MapCoordinate(latitude: 47.514128 ,longitude: 19.059922), rating: 0, status: "Opened", ratings: []))
//         getToilets()
        
    }
    
    
    func mapView(_ mapView: MKMapView, regionDidChangeAnimated animated: Bool) {
        //print(mapView.region)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK: spinningBar status
    private func processStarted(){
        dataComing.startAnimating()
        mapView.isHidden = true
    }
    
    private func processTerminated(){
        dataComing.stopAnimating()
        mapView.isHidden = false
    }
    
    //MARK: get toilets from server
    private func getToilets(){
        if let url = URL(string : URLStorage.getToilets){
            processStarted()
            URLSession.shared.dataTask(with: url) { data, response, err in
                if let data = data{
                    do{
                        let newToilets = try JSONDecoder().decode([BasicToilet].self, from: data)
                        DispatchQueue.main.async {
                            self.dataComing.stopAnimating()
                            self.toilets = newToilets
                            print(newToilets)
                        }
                    }catch let jsonErr{
                        print("Error serializing json:" ,jsonErr)
                    }
                }
                }.resume()
        }

    }
    

//
//    private func postNewToilet(of toilet:BasicToilet){
//        if let url = URL(string: URLStorage.addToilet){
//            var request = URLRequest(url: url)
//            request.httpMethod = "POST"
//            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//            if let jsonBody = try? JSONEncoder().encode(toilet.PostForm()){
//                request.httpBody = jsonBody
//                print(jsonBody.description)
//                URLSession.shared.dataTask(with: request) { data, response, err in
//                    }.resume()
//            }
//        }
//    }

}
